package ru.donspb.simplemap

import android.app.Application
import androidx.room.Room
import ru.donspb.simplemap.room.PointsDB
import ru.donspb.simplemap.room.PointsDao
import java.lang.IllegalStateException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: PointsDB? = null
        private const val DB_NAME = "PointsDataBase"

        fun getDatabase(): PointsDao {
            if (db == null) {
                synchronized(PointsDB::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            PointsDB::class.java,
                            DB_NAME
                        ).build()
                    }
                }
            }
            return db!!.pointsDao()
        }
    }
}