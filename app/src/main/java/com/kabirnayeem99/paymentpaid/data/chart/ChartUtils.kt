package com.kabirnayeem99.paymentpaid.data.chart

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.*
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.utils.CustomUtils

object ChartUtils {
    private const val TAG = "ChartUtils"

    private val colorTemplate = CustomUtils.getColorsFromTemplate()

    /**
     * This method will create a BarData object based on the payment list
     * @param paymentList of [Int] [List] and context of [Context]
     * @return [BarData]
     */
    fun getBarData(paymentList: List<Long>, context: Context): BarData {
        var position = 0f
        val barEntries = ArrayList<BarEntry>()

        for ((index, payment) in paymentList.withIndex()) {
            if (payment == 0L) {
                barEntries.add(BarEntry(index.toFloat(), 0f))
            } else {
                barEntries.add(BarEntry(index.toFloat(), payment.toFloat()))
            }
            barEntries.add(BarEntry(index.toFloat(), payment.toFloat()))
            position += 1f
        }

        val barDataSet = BarDataSet(barEntries, "Payments in Bar Template")

        barDataSet.valueTextColor = ContextCompat.getColor(context, R.color.material_black)
        barDataSet.color = ContextCompat.getColor(context, R.color.material_black)
        barDataSet.colors = colorTemplate

        return BarData(barDataSet)
    }


    /**
     * This method will create a PieData object based on the payment list
     * @param paymentList of [Int] [List] and context of [Context]
     * @return [PieData]
     */
    fun getPieData(paymentList: List<Long>, context: Context): PieData {

        val pieEntries = ArrayList<PieEntry>()

        var position = 0f
        paymentList.forEach {
            if (it > 0) {
                pieEntries.add(PieEntry(it.toFloat(), CustomUtils.getCurrentMonthName(position.toInt())))
            }
            position += 1f
        }

        val pieDataSet = PieDataSet(pieEntries, "Payments Comparison in Pie By month")
        pieDataSet.valueTextColor = ContextCompat.getColor(context, R.color.material_black)
        pieDataSet.color = ContextCompat.getColor(context, R.color.material_black)
        pieDataSet.colors = colorTemplate

        return PieData(pieDataSet)
    }
}