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

import java.io.Serializable;
import java.util.Objects;

public class GenderSample implements Serializable {

  private final String gender;
  private final String[] context;

  public GenderSample(String gender, String[] context) {
    this.gender = Objects.requireNonNull(gender, "gender must not be null");
    this.context = Objects.requireNonNull(context, "context must not be null");
  }

  public String getGender() {
    return gender;
  }

  public String[] getContext() {
    return context;
  }

  @Override
  public String toString() {
    return gender + '\t' +  context;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContext(), getGender());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof GenderSample) {
      GenderSample a = (GenderSample) obj;

      return getGender().equals(a.getGender())
              && getContext().equals(a.getContext());
    }

    return false;
  }
}
