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

import opennlp.tools.sentdetect.EmptyLinePreprocessorStream;
import opennlp.tools.util.FilterObjectStream;
import opennlp.tools.util.ObjectStream;

public class GenderSampleStream extends FilterObjectStream<String,GenderSample> {

  public GenderSampleStream(ObjectStream<String> rows) {
    super(new EmptyLinePreprocessorStream(rows));
  }

  @Override
  public GenderSample read() throws IOException {

    String row = samples.read();
    if (row != null && !"".equals(row)) {
      String[] tuple = row.split("\\t");
      if (tuple != null && tuple.length == 2)
        return new GenderSample(tuple[1], tuple[0].split("\\s+"));
    }
    return null;
  }
}
