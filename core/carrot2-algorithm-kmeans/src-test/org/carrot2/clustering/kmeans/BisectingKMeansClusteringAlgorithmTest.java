package org.carrot2.clustering.kmeans;

import static org.carrot2.core.test.assertions.Carrot2CoreAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Document;
import org.carrot2.core.test.ClusteringAlgorithmTestBase;
import org.junit.Test;

import com.google.common.collect.Lists;

public class BisectingKMeansClusteringAlgorithmTest extends
    ClusteringAlgorithmTestBase<BisectingKMeansClusteringAlgorithm>
{
    @Override
    public Class<BisectingKMeansClusteringAlgorithm> getComponentClass()
    {
        return BisectingKMeansClusteringAlgorithm.class;
    }

    @Test
    public void smokeTest()
    {
        final List<Document> documents = Lists.newArrayList();
        documents.add(new Document("WordA . WordA"));
        documents.add(new Document("WordB . WordB"));
        documents.add(new Document("WordC . WordC"));
        documents.add(new Document("WordA . WordA"));
        documents.add(new Document("WordB . WordB"));
        documents.add(new Document("WordC . WordC"));

        BisectingKMeansClusteringAlgorithmDescriptor.attributeBuilder(
            processingAttributes).labelCount(1).partitionCount(3);
        final List<Cluster> clusters = cluster(documents).getClusters();

        assertNotNull(clusters);
        assertEquals(3, clusters.size());
        assertThat(clusters.get(0)).hasLabel("WordA");
        assertThat(clusters.get(1)).hasLabel("WordB");
        assertThat(clusters.get(2)).hasLabel("WordC");
    }
}