package opennlp.tools.gender;

import opennlp.tools.ml.maxent.ContextGenerator;
import opennlp.tools.util.BaseToolFactory;
import opennlp.tools.util.InvalidFormatException;

public class GenderDetectorFactory extends BaseToolFactory {

  public ContextGenerator<String[]> getContextGenerator() {
    return new DefaultGenderDetectorContextGenerator();
  }

  public static GenderDetectorFactory create() {
    // extension loader?
    return new GenderDetectorFactory();
  }

  @Override
  public void validateArtifactMap() throws InvalidFormatException {
    // no additional artifacts
  }
}
