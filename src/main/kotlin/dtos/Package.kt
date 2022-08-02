package dtos

data class Package(val Description: String, val FirstSubmitted: String, val ID: Int, val LastModified: Int, val Maintainer: String, val Name: String, val NumVotes: Int, val OutOfDate: Boolean, val PackageBase: String, val PackageBaseID: Int, val Popularity: Float, val URL: String, val URLPath: String, val Version: String)
