package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers
import com.djordjekrutil.mozzartgreek.feature.repository.DrawsSelectedNumbersRepository
import javax.inject.Inject

class GetAllDrawsSelectedNumbersUseCase @Inject constructor(private val repository: DrawsSelectedNumbersRepository.IDatabase) :
    UseCase<List<DrawWithSelectedNumbers>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<DrawWithSelectedNumbers>> {
        val allDraws = repository.getAllDrawsSelectedNumbers()
        return if (repository.getAllDrawsSelectedNumbers().isNullOrEmpty()) {
            Either.Right(emptyList())
        } else {
            Either.Right(allDraws!!)
        }
    }
}