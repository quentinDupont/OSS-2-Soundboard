package fr.legrand.oss117soundboard.data.entity

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

import fr.legrand.oss117soundboard.data.manager.db.DatabaseManager

/**
 * Created by Benjamin on 30/09/2017.
 */
@Table(database = DatabaseManager::class)
data class Reply(
        @Column @PrimaryKey var id: Int = 0,
        @Column var name: String = "",
        @Column var description: String = "",
        @Column var isFavorite: Boolean = false,
        @Column var listenCount: Int = 0,
        @Column var timestamp: Int = 0
) : BaseModel() {

    constructor(id: Int, name: String, description: String, timestamp: Int) : this() {
        this.id = id
        this.name = name
        this.description = description
        this.timestamp = timestamp
        this.isFavorite = false
    }

}
