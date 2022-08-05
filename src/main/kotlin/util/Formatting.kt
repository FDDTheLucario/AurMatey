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

package util;

import dtos.RawPackage
import dtos.RawPackageInfo
import java.time.Instant;
import java.time.ZoneId;
import kotlin.math.ceil

object Formatting {
    fun toDate(epoch: Long): String {
        return Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString();
    }
    fun pages(sourceList: List<RawPackage>): List<List<RawPackage>> {
        return sourceList.chunked(10);
    }
}