package opennlp.tools.gender;

import opennlp.tools.ml.AbstractEventTrainer;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.ml.TrainerFactory;
import opennlp.tools.ml.maxent.ContextGenerator;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenderDetectorME implements GenderDetector {

  private MaxentModel model;

  private final ContextGenerator contextGenerator;

  public GenderDetectorME(GenderDetectorModel model, GenderDetectorFactory factory) {
    this.model = model.getMaxentModel();
    this.contextGenerator = model.getFactory().getContextGenerator();
  }

  @Override
  public String genderDetect(String s) {
    // TODO: missing impl
    return null;
  }

  public static GenderDetectorModel train(ObjectStream<GenderSample> samples,
                                          TrainingParameters mlParams,
                                          GenderDetectorFactory factory)
          throws IOException {

    Map<String, String> manifestInfoEntries = new HashMap<>();

    mlParams.putIfAbsent(AbstractEventTrainer.DATA_INDEXER_PARAM,
            AbstractEventTrainer.DATA_INDEXER_ONE_PASS_VALUE);

    EventTrainer trainer = TrainerFactory.getEventTrainer(mlParams, manifestInfoEntries);

    MaxentModel model = trainer.train(
            new GenderDetectorEventStream(samples, factory.getContextGenerator()));

    return new GenderDetectorModel(model, manifestInfoEntries, factory);

  }
}
