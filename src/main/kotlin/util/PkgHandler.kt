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
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.lordcodes.turtle.ShellRunException
import com.lordcodes.turtle.shellRun
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import kotlinx.serialization.json.Json;
import dtos.RawPackage;
import dtos.RawPackageInfo;
import errors.PackageNotFoundError;
import jdk.internal.org.objectweb.asm.TypeReference
import kotlinx.serialization.decodeFromString;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


object PkgHandler {
    private val httpClient: CloseableHttpClient = HttpClients.createDefault();
    private val mapper = jacksonObjectMapper();
    fun searchForPackage(packageName: String): List<RawPackage> {
        val packageToFind = HttpGet("https://aur.archlinux.org/rpc/?v=5&type=search&arg=$packageName");
        httpClient.execute(packageToFind).use { resp ->
            val respBody = JSONObject(EntityUtils.toString(resp.entity)).get("results").toString();
            if (respBody == "[]") throw PackageNotFoundError(); // jank! should ideally check if this is empty.
            return mapper.readValue(respBody);
        }
    }
    fun findPackage(packageName: String): List<RawPackageInfo> {
        val packageToFind = HttpGet("https://aur.archlinux.org/rpc/?v=5&type=info&arg=${packageName}");
        httpClient.execute(packageToFind).use { resp ->
            val respBody = JSONObject(EntityUtils.toString(resp.entity)).get("results").toString();
            println(respBody);
            if (respBody == "[]") throw PackageNotFoundError();
            return mapper.readValue(respBody);
        }
    }
    fun RawPackageInfo.getMissingDependencies(): ArrayList<String> {

        val missingDependencies = ArrayList<String>();
        if (this.Depends == null) {
            return missingDependencies;
        }
        this.Depends.forEach { dependency ->
            try {
                shellRun("pacman", listOf("-Q", dependency))
            } catch (e: ShellRunException) {
                if ("not found" in e.errorText) {
                    missingDependencies.add(dependency);
                } else {
                    println("catastrophic failure");
                }
            }
        }
        return missingDependencies;
    }
}
