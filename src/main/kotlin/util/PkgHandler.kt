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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject
import kotlinx.serialization.json.Json;
import dtos.RawPackage;
import dtos.RawPackageInfo
import errors.PackageNotFoundError
import kotlinx.serialization.decodeFromString


object PkgHandler {
    private val httpClient: CloseableHttpClient = HttpClients.createDefault();
    fun searchForPackage(packageName: String): List<RawPackage> {
        val packageToFind = HttpGet("https://aur.archlinux.org/rpc/?v=5&type=search&arg=$packageName");
        httpClient.execute(packageToFind).use { resp ->
            val respBody = JSONObject(EntityUtils.toString(resp.entity)).get("results");
            if (respBody.toString() == "[]") throw PackageNotFoundError(); // jank! should ideally check if this is empty.
            return Json.decodeFromString(respBody.toString());
        }
    }
    fun findPackage(packageName: String): List<RawPackageInfo> {
        val packageToFind = HttpGet("https://aur.archlinux.org/rpc/?v=5&type=info&arg=${packageName}");
        httpClient.execute(packageToFind).use { resp ->
            val respBody = JSONObject(EntityUtils.toString(resp.entity)).get("results");
            println(respBody);
            if (respBody.toString() == "[]") throw PackageNotFoundError();
            println(respBody.toString().length);
            return Json.decodeFromString(respBody.toString());
        }
    }
}
