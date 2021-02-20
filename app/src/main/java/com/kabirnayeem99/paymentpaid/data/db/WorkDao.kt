package com.kabirnayeem99.paymentpaid.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

/**
 * WorkDao or Work Data Access Objects are used to access the persisted data.
 * saved in the Sqlite database
 * It is the modern replacement of database query builders or direct queries
 */
@Dao
interface WorkDao {
    @Insert
    suspend fun insert(work: Work)

    @Update
    suspend fun update(work: Work)

    @Delete
    suspend fun delete(work: Work)

    @Query(value = "SELECT * FROM works_db_table ORDER BY submission_date DESC")
    fun getAllWorks(): LiveData<List<Work>>

    // Gets the payment by month, and sets a 0, where there is no work in this particular month
    @Query("SELECT IFNULL(SUM(w.payment), 0) FROM (SELECT 1 month UNION ALL SELECT 2 UNION ALL" +
            " SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 " +
            "UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL" +
            " SELECT 12) m LEFT JOIN works_db_table w ON w.account_month =  m.month AND " +
            "w.account_year = :year GROUP BY m.month")
    fun getTotalPaymentByMonth(year: Int): LiveData<List<Int>>

    // Gets the payment summation where the the year is the current year
    @Query("SELECT SUM(payment) FROM works_db_table WHERE account_year = :year ")
    fun getTotalPaymentByYear(year: Int): LiveData<Int>
}