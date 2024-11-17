package io.bahuma.kassenkumpel.feature_pointofsale.domain.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import com.sumup.merchant.reader.api.SumUpAPI
import com.sumup.merchant.reader.api.SumUpPayment

class SumUpPaymentForResult : ActivityResultContract<SumUpPayment, ActivityResult>() {
    override fun createIntent(
        context: Context,
        input: SumUpPayment
    ): Intent {
        class DummyActivity : Activity() {
            var capturedIntent: Intent? = null
            override fun startActivityForResult(intent: Intent?, code: Int) {
                capturedIntent = intent
            }

            override fun getPackageName(): String? {
                return context.applicationContext.packageName
            }
        }

        val dummy = DummyActivity()
        SumUpAPI.checkout(dummy, input, 2)
        return dummy.capturedIntent!!
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): ActivityResult = ActivityResult(resultCode, intent)

}