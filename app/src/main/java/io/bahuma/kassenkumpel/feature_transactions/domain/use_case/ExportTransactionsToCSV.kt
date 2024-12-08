package io.bahuma.kassenkumpel.feature_transactions.domain.use_case

import com.opencsv.CSVWriter
import io.bahuma.kassenkumpel.feature_transactions.domain.exception.ExportTransactionsException
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems
import okio.IOException
import java.io.OutputStream

class ExportTransactionsToCSV {
    operator fun invoke(
        transactions: List<TransactionWithTransactionLineItems>,
        transactionsCsvOutputStream: OutputStream,
        transactionLineItemsCsvOutputStream: OutputStream
    ) {
        exportTransactions(transactions, transactionsCsvOutputStream)
        exportTransactionLineItems(transactions, transactionLineItemsCsvOutputStream)
    }

    private fun exportTransactions(
        transactions: List<TransactionWithTransactionLineItems>,
        outputStream: OutputStream
    ) {
        val writer: CSVWriter

        try {
            writer = CSVWriter(outputStream.writer())

            writer.writeNext(
                arrayOf(
                    "ID",
                    "Timestamp",
                    "Amount",
                    "Payment Method",
                    "External Transaction ID"
                )
            )

            val data = arrayListOf<List<String>>()
            data.add(listOf(""))

            for (transaction in transactions) {
                writer.writeNext(
                    arrayOf(
                        transaction.transaction.transactionId.toString(),
                        transaction.transaction.timestamp.toString(),
                        transaction.transaction.amount.toString(),
                        transaction.transaction.paymentMethod.toString(),
                        transaction.transaction.externalTransactionId.toString()
                    ), false
                )
            }

            writer.close()
            outputStream.close()
        } catch (e: IOException) {
            throw ExportTransactionsException(e)
        }
    }

    private fun exportTransactionLineItems(
        transactions: List<TransactionWithTransactionLineItems>,
        outputStream: OutputStream
    ) {
        val writer: CSVWriter

        try {
            writer = CSVWriter(outputStream.writer())

            writer.writeNext(
                arrayOf(
                    "ID",
                    "Transaction ID",
                    "Product ID",
                    "Name",
                    "Amount",
                    "Price Per Unit",
                    "Deposit Per Unit",
                    "Total"
                )
            )

            val data = arrayListOf<List<String>>()
            data.add(listOf(""))

            for (transaction in transactions) {
                for (lineItem in transaction.lineItems) {
                    writer.writeNext(
                        arrayOf(
                            lineItem.transactionLineItemId.toString(),
                            lineItem.transactionId.toString(),
                            lineItem.productId.toString(),
                            lineItem.name,
                            lineItem.amount.toString(),
                            lineItem.pricePerUnit.toString(),
                            lineItem.depositPerUnit.toString(),
                            ((lineItem.pricePerUnit + (lineItem.depositPerUnit
                                ?: 0.0)) * lineItem.amount).toString()
                        ), false
                    )
                }

            }

            writer.close()
            outputStream.close()
        } catch (e: IOException) {
            throw ExportTransactionsException(e)
        }
    }
}