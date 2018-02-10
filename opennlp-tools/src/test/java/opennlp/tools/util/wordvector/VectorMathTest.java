package opennlp.tools.util.wordvector;

import org.junit.Assert;
import org.junit.Test;

public class VectorMathTest {

  double epsilon = 1.0E-5;
  final double[] d1 = {1.0, 2.0, 3.0, 4.0, 5.0};
  final double[] d2 = {2.0, 3.0, 4.0, 5.0, 6.0};

  final double[] d1Opposite = {-1.0, -2.0, -3.0, -4.0, -5.0};
  final double[] d1Same = {1.0, 2.0, 3.0, 4.0, 5.0};

  final double[] d3Orthogonal = {-3.0, 4.0};
  final double[] d4Orthogonal = {4.0, 3.0};

  final float[] f1 = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f};
  final float[] f2 = {2.0f, 3.0f, 4.0f, 5.0f, 6.0f};

  final DoubleArrayVector dav1 = new DoubleArrayVector(d1);
  final DoubleArrayVector dav2 = new DoubleArrayVector(d2);
  final DoubleArrayVector dav1Opposite = new DoubleArrayVector(d1Opposite);
  final DoubleArrayVector dav1Same = new DoubleArrayVector(d1Same);
  final DoubleArrayVector dav3Orthogonal = new DoubleArrayVector(d3Orthogonal);
  final DoubleArrayVector dav4Orthogonal = new DoubleArrayVector(d4Orthogonal);

  final FloatArrayVector fav1 = new FloatArrayVector(f1);
  final FloatArrayVector fav2 = new FloatArrayVector(f2);

  @Test
  public void subtractDoubleArrayVectorTest() {
    WordVector actual = VectorMath.substract(dav2,dav1);
    for(int i=0; i<actual.dimension(); i++)
      Assert.assertEquals(actual.getAsDouble(i), 1.0, epsilon);
  }

  @Test
  public void subtractFloatArrayVectorTest() {
    WordVector actual = VectorMath.substract(fav2,fav1);
    for(int i=0; i<actual.dimension(); i++)
      Assert.assertEquals(actual.getAsFloat(i), 1.0f, epsilon);
  }

  @Test
  public void cosineSimilarityTest() {

    double actual = VectorMath.cosineSimilarity(dav1, dav1Opposite);
    Assert.assertEquals(-1.0d, actual, epsilon);

    actual = VectorMath.cosineSimilarity(dav1, dav1Same);
    Assert.assertEquals(1.0d, actual, epsilon);

    actual = VectorMath.cosineSimilarity(dav3Orthogonal, dav4Orthogonal);
    Assert.assertEquals(0.0d, actual, epsilon);
  }

}
