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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import opennlp.tools.ml.model.AbstractModel;
import opennlp.tools.ml.model.MaxentModel;
import opennlp.tools.util.BaseToolFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.model.BaseModel;

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

