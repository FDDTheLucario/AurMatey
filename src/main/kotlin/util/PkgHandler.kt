package util;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject
import kotlinx.serialization.json.Json;
import dtos.Package;
import kotlinx.serialization.decodeFromString


object PkgHandler {
    private val httpClient: CloseableHttpClient = HttpClients.createDefault();
    fun findPackage(packageName: String) {
        val packageToFind = HttpGet("https://aur.archlinux.org/rpc/?v=5&type=search&arg=${packageName}");
        httpClient.execute(packageToFind).use { resp ->
            val respBody = JSONObject(EntityUtils.toString(resp.entity)).get("results");
            println(respBody);
          
            val packages = Json.decodeFromString<List<Package>>(respBody.toString());
            for (pkg in packages) println("Package name: ${pkg.Name}");
        }
    }
}
