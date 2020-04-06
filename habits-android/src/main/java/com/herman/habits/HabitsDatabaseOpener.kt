/*
 * Copyright (C) 2018 Herman <ringtone.sky@gmail.com>
 *
 * This file is part of 7-Habit Tracker.
 *
 * 7-Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * 7-Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.herman.habits

import android.content.*
import android.database.sqlite.*

import com.herman.habits.core.database.*
import com.herman.habits.database.*

class HabitsDatabaseOpener(
        context: Context,
        databaseFilename: String,
        private val version: Int
) : SQLiteOpenHelper(context, databaseFilename, null, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.disableWriteAheadLogging()
        db.version = 8
        onUpgrade(db, -1, version)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.disableWriteAheadLogging()
    }

    override fun onUpgrade(db: SQLiteDatabase,
                           oldVersion: Int,
                           newVersion: Int) {
        db.disableWriteAheadLogging()
        if (db.version < 8) throw UnsupportedDatabaseVersionException()
        val helper = MigrationHelper(AndroidDatabase(db))
        helper.migrateTo(newVersion)
    }

    override fun onDowngrade(db: SQLiteDatabase,
                             oldVersion: Int,
                             newVersion: Int) {
        throw UnsupportedDatabaseVersionException()
    }
}
