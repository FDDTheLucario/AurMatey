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


import util.PkgHandler;

fun main(args: Array<String>) {
    when (args[0]) {
        "-s" -> { // search
            val packageToFind = args[1];
            if (packageToFind.isEmpty()) {
                println("aurmatey: error: must provide package name");
            } else {
                val packages = PkgHandler.searchForPackage(packageToFind);
                println("aurmatey: found ${packages.size} results. show?");
            }
        }
    }
}
