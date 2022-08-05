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


import dtos.Package
import errors.PackageNotFoundError
import util.Formatting
import util.PkgHandler;
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    when (args[0]) {
        "-s" -> { // search
                search(args[1]);
            }
        }
    }
fun search(name: String) {
    if (name.isEmpty()) {
        println("aurmatey: error: must provide package name");
    } else {
        try {
            val packages = PkgHandler.searchForPackage(name);
            print("aurmatey: found ${packages.size} results. show? [Y/n] ");
            val pages = Formatting.pages(packages, 10);
            val input = readln();
            when (input.lowercase(Locale.getDefault())) {
                "y" -> {
                    for (pkg in packages) {
                        println(Package(pkg).getBasicInfo() + "\n"); // how could i implement pagination?
                        println("Debugging: ${pages.size}")
                    }
                }
                "n" -> {
                    exitProcess(0);
                }
                "" -> {
                    for (pkg in packages) {
                        println(Package(pkg).getBasicInfo() + "\n"); // how could i implement pagination?
                    }
                }
                else -> {
                    println("aurmatey: invalid option. aborting...");
                    exitProcess(1);
                }
            }
        } catch (e: PackageNotFoundError) {
            println("aurmatey: no packages found");
        }
    }
}
