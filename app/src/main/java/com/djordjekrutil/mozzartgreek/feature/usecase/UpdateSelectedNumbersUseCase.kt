package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers
import com.djordjekrutil.mozzartgreek.feature.repository.DrawsSelectedNumbersRepository
import javax.inject.Inject

class UpdateSelectedNumbersUseCase @Inject constructor(private val repository: DrawsSelectedNumbersRepository.IDatabase) :
    UseCase<UseCase.None, DrawWithSelectedNumbers>() {

    override suspend fun run(params: DrawWithSelectedNumbers): Either<Failure, None> =
        repository.updateSelectedNumbers(params)
}