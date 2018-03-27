/**
 * Created by wliu on 11/21/17.
 */
package com.intrence.backend.productcatalog.resources;

import com.google.inject.Inject;
import com.intrence.core.elasticsearch.ElasticSearchService;
import com.intrence.core.persistence.dao.ProductDao;
import com.intrence.core.util.AppUtils;
import com.intrence.models.model.Product;
import com.intrence.models.util.Constants;
import org.apache.lucene.search.Query;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.SearchHits;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.UUID;

@Path("/product-catalog/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ProductCatalogResource {

    private final ProductDao productDao;
    private final ElasticSearchService elasticSearchService;

    @Inject
    public ProductCatalogResource(ProductDao productDao, ElasticSearchService elasticSearchService) {
        this.productDao = productDao;
        this.elasticSearchService = elasticSearchService;
    }

    @GET
    @Path("status")
    public Response checkStatus() {
        return Response.ok("Status: Live").build();
    }

    @GET
    @Path("product/{id}")
    public Response getProduct(@PathParam("id") String id) throws Exception {

        UUID productId = AppUtils.validateAndReturnUUID(id);

        Product product = null;
        try {
            // Get document from ES
            GetResponse response = this.elasticSearchService.getDocument(Constants.PRODUCTS_STRING, Constants.DOC_STRING, productId.toString());
            if (!response.isSourceEmpty()) {
                product = Product.fromJson(response.getSourceAsString());
                if (product != null) {
                    return Response.ok(product.toJson()).build();
                }
            }
            // Get from DB
            product = this.productDao.getProductById(productId);
            if (product != null) {
                return Response.ok(product.toJson()).build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product", e);
        }

        throw new NotFoundException(String.format("The product with id %s cannot be found", productId));
    }

    @POST
    @Path("product/{id}")
    public Response upsertProduct() throws Exception {






        return Response.ok().build();
    }

    @DELETE
    @Path("product/{id}")
    public Response deleteProduct(@PathParam("id") String id) throws Exception {

        UUID productId = AppUtils.validateAndReturnUUID(id);

        this.elasticSearchService.deleteDocument(Constants.PRODUCTS_STRING, Constants.DOC_STRING, productId.toString());
        this.productDao.deleteProductById(productId);

        return Response.ok("Deleted product: " + id).build();
    }

    @GET
    @Path("product/search")
    public Response searchProducts() throws Exception {

        QueryBuilder query = null;
        SearchHits searchHits = this.elasticSearchService.searchDocuments(Constants.PRODUCTS_STRING, Constants.DOC_STRING, null, null, 0, 0);
        return Response.ok().build();
    }

}
