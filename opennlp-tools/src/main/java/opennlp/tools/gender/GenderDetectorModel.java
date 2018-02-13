package opennlp.tools.gender;

import opennlp.tools.ml.model.AbstractModel;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.util.BaseToolFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.model.BaseModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class GenderDetectorModel extends BaseModel {

  private static final String COMPONENT_NAME = "GenderDetectorME";
  private static final String GENDERDETECT_MODEL_ENTRY_NAME = "genderdetect.model";

  public GenderDetectorModel(MaxentModel genderdetectModel,
                             Map<String, String> manifestInfoEntries,
                             GenderDetectorFactory factory) {
    // lang code "und"?
    super(COMPONENT_NAME, "und", manifestInfoEntries, factory);

    artifactMap.put(GENDERDETECT_MODEL_ENTRY_NAME, genderdetectModel);
    checkArtifactMap();
  }

  public GenderDetectorModel(InputStream in) throws IOException {
    super(COMPONENT_NAME, in);
  }

  public GenderDetectorModel(File modelFile) throws IOException {
    super(COMPONENT_NAME, modelFile);
  }

  public GenderDetectorModel(URL modelURL) throws IOException {
    super(COMPONENT_NAME, modelURL);
  }

  @Override
  protected void validateArtifactMap() throws InvalidFormatException {
    super.validateArtifactMap();

    if (!(artifactMap.get(GENDERDETECT_MODEL_ENTRY_NAME) instanceof AbstractModel)) {
      throw new InvalidFormatException("Language detector model is incomplete!");
    }
  }

  public GenderDetectorFactory getFactory() {
    return (GenderDetectorFactory) this.toolFactory;
  }

  @Override
  protected Class<? extends BaseToolFactory> getDefaultFactory() {
    return GenderDetectorFactory.class;
  }

  public MaxentModel getMaxentModel() {
    return (MaxentModel) artifactMap.get(GENDERDETECT_MODEL_ENTRY_NAME);
  }
}

