/*
 * AurMatey - An AUR helper written in Kotlin.
 * Copyright (C) 2022 Richard Moch
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dtos;

import kotlinx.serialization.Serializable;

@Serializable
data class RawPackageInfo(val Conflicts: Array<String>?,
                          val Depends: Array<String>,
                          val Description: String,
                          val FirstSubmitted: Long,
                          val ID: Int,
                          val Keywords: Array<String>,
                          val LastModified: Long,
                          val License: Array<String>,
                          val Maintainer: String?,
                          val MakeDepends: Array<String>,
                          val Name: String,
                          val NumVotes: Int,
                          val OptDepends: Array<String>,
                          val OutOfDate: Long?,
                          val PackageBase: String,
                          val PackageBaseID: Int,
                          val Popularity: Float,
                          val Provides: Array<String>,
                          val URL: String?,
                          val URLPath: String,
                          val Version: String);