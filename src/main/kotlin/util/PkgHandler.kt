package util;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients;

object PkgHandler {
    private val httpClient: CloseableHttpClient = HttpClients.createDefault();
    fun findPackage(packageName: String) {
        val packageToFind = HttpGet("https://aur.archlinux.org/rpc/?v=5&type=search&arg=${packageName}");
        httpClient.execute(packageToFind).use { resp ->
            resp.entity
        }
    }
}
