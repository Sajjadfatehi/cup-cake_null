package com.database.comment

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CommentDataBaseDao {
    @Insert
    fun insertComment(Comment: CommentDataBase)

    @Delete
    fun deleteComment(vararg comment: CommentDataBase)

    @Update
    fun updateComment(vararg comment: CommentDataBase)

    @Query("SELECT * FROM comment_table")
    fun getAllComment(): LiveData<List<CommentDataBase>>

//    @Query("SELECT * FROM comment_table WHERE text_comment LIKE :name")
//    fun searchComment(name: String) : LiveData<List<CommentDataBase>>

//    @Query(
//        "select DISTINCT a.* " +
//                "from Comments as a " +
//                "join keywords_and_Comments as ka on ka.Comment_id = a.Comment_id " +
//                "join keywords as k on k.keyword_id = ka.keyword_id " +
//                "join comments as c on c.Comment_id = a.Comment_id " +
//                "where c.content like :word or k.title like :word or a.content like :word or a.title like :word "
//    )
//    fun searchComment(word: String): LiveData<MutableList<CommentDataModel>>
//
//    data class CommentDataModel(
//        @Embedded val Comment: CommentEntity,
//        @Relation(
//            entity = UserEntity::class,
//            parentColumn = "owner_id",
//            entityColumn = "user_id"
//        ) val user: UserEntity,
//        @Relation(
//            entity = CommentEntity::class,
//            parentColumn = "Comment_id",
//            entityColumn = "Comment_id"
//        ) val comments: MutableList<CommentEntity>,
//        @Relation(
//            entity = LikeEntity::class,
//            parentColumn = "Comment_id",
//            entityColumn = "Comment_id"
//        ) val likes: MutableList<LikeEntity>,
//        @Relation(
//            entity = KeywordsAndComments::class,
//            parentColumn = "Comment_id",
//            entityColumn = "Comment_id"
//        )
//        val keywords: MutableList<KeywordsAndComments>
//
//        @Query(
//    "SELECT users.*, x.num " +
//    "from users ,( " +
//    "SELECT owner_id as owner, COUNT(*) as num " +
//    "from Comments join users on user_id = owner_id " +
//    "GROUP BY user_id) as x" +
//    " WHERE user_id = x.owner"
//    )
//    fun getUserWithCommentCount(): LiveData<MutableList<UserAndCommentsNumber>>


//    @Query("SELECT * FROM Comment_data_base WHERE CommentId = :CommentId ")
//    fun getAllComment(CommentId: Long): LiveData<List<PersonCommentModelEntity>>
/*
    @Query("DELETE FROM  Comment_data_base")
    fun deleteAll()*/
}