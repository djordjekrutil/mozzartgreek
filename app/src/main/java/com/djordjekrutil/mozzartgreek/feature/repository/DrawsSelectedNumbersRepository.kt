package com.djordjekrutil.mozzartgreek.feature.repository

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.db.AppDatabase
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers
import javax.inject.Inject

interface DrawsSelectedNumbersRepository {

    interface IDatabase {
        fun insertSelectedNumbers(drawWithSelectedNumbers: DrawWithSelectedNumbers): Either<Failure, UseCase.None>
        fun updateSelectedNumbers(drawWithSelectedNumbers: DrawWithSelectedNumbers): Either<Failure, UseCase.None>
        fun deleteSelectedNumbers(drawId: Int): Either<Failure, UseCase.None>
        fun getAllDrawsSelectedNumbers(): List<DrawWithSelectedNumbers>?
        fun getSelectedNumbersByDrawId(drawId: Long): DrawWithSelectedNumbers?
    }

    class Database @Inject constructor(
        private val appDatabase: AppDatabase
    ) : IDatabase {
        override fun insertSelectedNumbers(drawWithSelectedNumbers: DrawWithSelectedNumbers): Either<Failure, UseCase.None> {
            appDatabase.SelectedNumbersDao().insertSelectedNumbers(drawWithSelectedNumbers)
            return Either.Right(UseCase.None())
        }

        override fun updateSelectedNumbers(drawWithSelectedNumbers: DrawWithSelectedNumbers): Either<Failure, UseCase.None> {
            appDatabase.SelectedNumbersDao().updateSelectedNumbers(drawWithSelectedNumbers)
            return Either.Right(UseCase.None())
        }

        override fun deleteSelectedNumbers(drawId: Int): Either<Failure, UseCase.None> {
            appDatabase.SelectedNumbersDao().deleteDrawSelectedNumbers(drawId)
            return Either.Right(UseCase.None())
        }

        override fun getAllDrawsSelectedNumbers(): List<DrawWithSelectedNumbers>? =
            appDatabase.SelectedNumbersDao().getAllDrawsSelectedNumbers()


        override fun getSelectedNumbersByDrawId(drawId: Long): DrawWithSelectedNumbers? =
            appDatabase.SelectedNumbersDao().getSelectedNumbersById(drawId)

    }
}