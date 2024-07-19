package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.repository.DrawsSelectedNumbersRepository
import javax.inject.Inject

class DeleteDrawByIdUseCase @Inject constructor(private val repository: DrawsSelectedNumbersRepository.IDatabase) :
    UseCase<UseCase.None, Int>() {
    override suspend fun run(params: Int): Either<Failure, None> =
        repository.deleteSelectedNumbers(params)
}