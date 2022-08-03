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
import java.time.*;
class Package(private val rawPackage: RawPackage) {
    val description = rawPackage.Description;
    val submissionDate = toDate(rawPackage.FirstSubmitted);
    val lastModified = toDate(rawPackage.LastModified);
    val maintainer = rawPackage.Maintainer ?: "No maintainer";
    val packageName = rawPackage.Name;
    val outOfDate = rawPackage.OutOfDate ?: "Not out of date";
    val repoUrl = rawPackage.URL ?: "No URL";
    val aurUrl = "https://aur.archlinux.org${rawPackage.URLPath}";
    val version = rawPackage.Version;
    private fun toDate(epoch: Long): String {
        return Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString();
    }

    fun getBasicInfo():String {
        return "${packageName}-${version}" +
                "\n${description}";
    }
    fun getDetailedInfo(): String {
        return "${getBasicInfo()}\n\n" +
                "Uploaded on: $submissionDate\n" +
                "Last modified: $lastModified\n" +
                "Maintained by: $maintainer\n" +
                "Out of date since: $outOfDate\n" +
                "Repo url: $repoUrl\n" +
                "AUR url: $aurUrl";
    }
}