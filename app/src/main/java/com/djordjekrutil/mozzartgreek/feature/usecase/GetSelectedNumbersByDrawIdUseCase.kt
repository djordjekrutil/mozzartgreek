package com.djordjekrutil.mozzartgreek.feature.usecase

import com.djordjekrutil.mozzartgreek.core.exception.Failure
import com.djordjekrutil.mozzartgreek.core.functional.Either
import com.djordjekrutil.mozzartgreek.core.intrecator.UseCase
import com.djordjekrutil.mozzartgreek.feature.model.DrawWithSelectedNumbers
import com.djordjekrutil.mozzartgreek.feature.repository.DrawsSelectedNumbersRepository
import javax.inject.Inject

class GetSelectedNumbersByDrawIdUseCase @Inject constructor(private val repository: DrawsSelectedNumbersRepository.Database) :
    UseCase<DrawWithSelectedNumbers, Long>() {

    override suspend fun run(params: Long): Either<Failure, DrawWithSelectedNumbers> {
        val draw = repository.getSelectedNumbersByDrawId(params)
        return if (draw != null) {
            Either.Right(draw)
        } else {
            Either.Right(DrawWithSelectedNumbers(-1, emptyList()))
        }
    }
}