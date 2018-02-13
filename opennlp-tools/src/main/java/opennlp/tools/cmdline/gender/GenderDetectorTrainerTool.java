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

package opennlp.tools.cmdline.gender;

import java.io.File;
import java.io.IOException;

import opennlp.tools.cmdline.AbstractTrainerTool;
import opennlp.tools.cmdline.CmdLineUtil;
import opennlp.tools.cmdline.TerminateToolException;
import opennlp.tools.cmdline.gender.GenderDetectorTrainerTool.TrainerToolParams;
import opennlp.tools.cmdline.params.TrainingToolParams;
import opennlp.tools.gender.GenderDetectorFactory;
import opennlp.tools.gender.GenderDetectorME;
import opennlp.tools.gender.GenderDetectorModel;
import opennlp.tools.gender.GenderSample;
import opennlp.tools.ml.TrainerFactory;
import opennlp.tools.util.model.ModelUtil;

public final class GenderDetectorTrainerTool extends AbstractTrainerTool<GenderSample, TrainerToolParams> {

  interface TrainerToolParams extends TrainingParams, TrainingToolParams {

  }

  public GenderDetectorTrainerTool() {
    super(GenderSample.class, TrainerToolParams.class);
  }

  public String getShortDescription() {
    return "trainer for the learnable gender detector";
  }

  public void run(String format, String[] args) {
    super.run(format, args);

    mlParams = CmdLineUtil.loadTrainingParameters(params.getParams(), false);

    if (mlParams != null) {
      if (!TrainerFactory.TrainerType.EVENT_MODEL_TRAINER.equals(TrainerFactory.getTrainerType(mlParams))) {
        throw new TerminateToolException(1, "Sequence training is not supported!");
      }
    }

    if (mlParams == null) {
      mlParams = ModelUtil.createDefaultTrainingParameters();
    }

    File modelOutFile = params.getModel();
    CmdLineUtil.checkOutputFile("gender detector model", modelOutFile);

    GenderDetectorModel model;

    try {
      GenderDetectorFactory gdFactory = GenderDetectorFactory.create();
      model = GenderDetectorME.train("eng", sampleStream, mlParams, gdFactory);
    } catch (IOException e) {
      throw createTerminationIOException(e);
    }
    finally {
      try {
        sampleStream.close();
      } catch (IOException e) {
        // sorry that this can fail
      }
    }

    CmdLineUtil.writeModel("sentence detector", modelOutFile, model);

  }



}
