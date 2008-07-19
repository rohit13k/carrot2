package org.carrot2.clustering.lingo;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for label building in {@link ClusterBuilder}.
 */
public class ClusterLabelBuilderTest extends TermDocumentMatrixBuilderTestBase
{
    /** Matrix reducer needed for test */
    private TermDocumentMatrixReducer reducer;

    /** Label builder under tests */
    private ClusterBuilder clusterBuilder;

    @Before
    public void setUpClusterLabelBuilder()
    {
        clusterBuilder = new ClusterBuilder();
        reducer = new TermDocumentMatrixReducer();
    }

    @Test
    public void testEmpty()
    {
        check(new int [0]);
    }

    @Test
    public void testNoPhrases()
    {
        createDocuments("", "aa . aa", "", "bb . bb", "", "cc . cc");
        final int [] expectedFeatureIndex = new int []
        {
            0, 1, 2
        };

        reducer.desiredClusterCountBase = 30;
        check(expectedFeatureIndex);
    }

    @Test
    public void testSinglePhraseNoSingleWords()
    {
        createDocuments("aa bb", "aa bb", "aa bb", "aa bb");

        final int [] expectedFeatureIndex = new int []
        {
            2
        };

        reducer.desiredClusterCountBase = 10;
        check(expectedFeatureIndex);
    }

    @Test
    public void testSinglePhraseSingleWords()
    {
        createDocuments("aa bb", "aa bb", "cc", "cc", "aa bb", "aa bb");
        clusterBuilder.phraseLabelBoost = 0.5;

        final int [] expectedFeatureIndex = new int []
        {
            2, 3
        };

        reducer.desiredClusterCountBase = 15;
        check(expectedFeatureIndex);
    }

    @Test
    public void testQueryWordsRemoval()
    {
        createDocuments("query word . aa", "query word . aa", "query . word",
            "query . word . aa");
        clusterBuilder.phraseLabelBoost = 0.5;

        final int [] expectedFeatureIndex = new int []
        {
            0
        };

        reducer.desiredClusterCountBase = 10;
        createPreprocessingContext("query word");
        check(expectedFeatureIndex);
    }

    private void check(int [] expectedFeatureIndex)
    {
        buildTermDocumentMatrix();
        reducer.reduce(lingoContext);
        clusterBuilder.buildLabels(lingoContext, new TfTermWeighting());

        assertThat(lingoContext.clusterLabelFeatureIndex).as("clusterLabelFeatureIndex")
            .containsOnly(expectedFeatureIndex);
    }
}
