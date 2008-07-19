package org.carrot2.matrix.factorization;

import cern.colt.matrix.DoubleMatrix2D;

/**
 * Factory for {@link PartialSingularValueDecomposition}s.
 */
public class PartialSingularValueDecompositionFactory implements
    MatrixFactorizationFactory
{
    /** The desired number of base vectors */
    protected int k;

    /** The default desired number of base vectors */
    protected static final int DEFAULT_K = -1;

    /**
     * Creates the factory that creates factorizations that compute the maximum number of
     * base vectors.
     */
    public PartialSingularValueDecompositionFactory()
    {
        this.k = DEFAULT_K;
    }

    public MatrixFactorization factorize(DoubleMatrix2D A)
    {
        PartialSingularValueDecomposition partialSingularValueDecomposition = new PartialSingularValueDecomposition(
            A);

        partialSingularValueDecomposition.setK(k);
        partialSingularValueDecomposition.compute();

        return partialSingularValueDecomposition;
    }

    /**
     * Sets the number of base vectors <i>k </i>.
     * 
     * @param k the number of base vectors
     */
    public void setK(int k)
    {
        this.k = k;
    }

    /**
     * Returns the number of base vectors <i>k </i>.
     */
    public int getK()
    {
        return k;
    }
}