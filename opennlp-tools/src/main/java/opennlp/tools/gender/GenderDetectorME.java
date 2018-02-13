/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.gender;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.ml.AbstractEventTrainer;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.ml.TrainerFactory;
import opennlp.tools.ml.maxent.ContextGenerator;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;

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

  public static GenderDetectorModel train(String language, ObjectStream<GenderSample> samples,
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
