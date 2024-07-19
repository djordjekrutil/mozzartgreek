package com.djordjekrutil.mozzartgreek.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object DatabaseError : Failure()
    object None : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()

    class ServerFailure(val code: Int, val message: String): FeatureFailure()
}
