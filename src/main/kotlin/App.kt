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
import dtos.RawPackage
import dtos.RawPackageInfo
import errors.PackageNotFoundError
import util.Formatting
import util.PkgHandler;
import util.PkgHandler.getMissingDependencies
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    when (args[0]) {
        "-s" -> { // search
                search(args[1]);
            }
        "-S" -> { installPackage(PkgHandler.findPackage(args[1])[0]) }

        }
    }
fun search(name: String) {
    if (name.isEmpty()) {
        println("aurmatey: error: must provide package name");
    } else {
        try {
            val packages = PkgHandler.searchForPackage(name);
            print("aurmatey: found ${packages.size} results. show? [Y/n] ");
            val pages = Formatting.pages(packages);
            val input = readln();
            when (input.lowercase(Locale.getDefault())) {
                "y" -> {
                    list(packages);
                }
                "n" -> {
                    exitProcess(0);
                }
                "" -> {
                    list(packages);
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
fun listPage(packages: List<RawPackage>, page: Int) {
    val packagePage = Formatting.pages(packages);
    val maxPages = packagePage.size - 1;
    var currentPage = page;
    if (page > maxPages || page < 0) currentPage = 0;
    println("showing page ${currentPage + 1} of ${packagePage.size}");
    for (pkg in packagePage[currentPage]) println("${Package(pkg).getBasicInfo()}\n");
}

fun listPackages(packages: List<RawPackage>) {
    var input = readln();
    val maxPages = Formatting.pages(packages).size;
    while (input.lowercase(Locale.getDefault()) != "x") {
        try {
            if (input.toInt() > maxPages || input.toInt() < 1) input = "1";
            listPage(packages, input.toInt() - 1);
            print("[${input.toInt()}/$maxPages] (x to exit) ");
            listPackages(packages);
        } catch (e: NumberFormatException) {
            listPage(packages, 0);
            print("[1/$maxPages] (x to exit) ");
            listPackages(packages);
        }
    }
    if (input.lowercase(Locale.getDefault()) == "x") exitProcess(0);
}
fun list(packages: List<RawPackage>) {
    listPage(packages, 0);
    print("[1/${Formatting.pages(packages).size}] (x to exit) ");
    listPackages(packages);
}
fun installPackage(pkg: RawPackageInfo) {
    val missingDeps = pkg.getMissingDependencies();
    if (missingDeps.size == 0) {
        println("required dependencies all there");
    } else {
        val dependencies = missingDeps.joinToString(separator = ", ");
        println("${pkg.Name} requires the following dependencies: $dependencies");
    }
}