/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.nio.serialization.impl;

import com.hazelcast.nio.serialization.ClassDefinition;
import com.hazelcast.nio.serialization.FieldDefinition;
import com.hazelcast.nio.serialization.FieldType;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ClassDefinitionImpl implements ClassDefinition {

    private int factoryId;
    private int classId;
    private int version = -1;
    private final Map<String, FieldDefinition> fieldDefinitionsMap = new LinkedHashMap<String, FieldDefinition>();

    public ClassDefinitionImpl() {
    }

    public ClassDefinitionImpl(int factoryId, int classId, int version) {
        this.factoryId = factoryId;
        this.classId = classId;
        this.version = version;
    }

    public void addFieldDef(FieldDefinitionImpl fd) {
        fieldDefinitionsMap.put(fd.getName(), fd);
    }

    public FieldDefinition getField(String name) {
        return fieldDefinitionsMap.get(name);
    }

    public FieldDefinition getField(int fieldIndex) {
        if (fieldIndex < 0 || fieldIndex >= fieldDefinitionsMap.size()) {
            throw new IndexOutOfBoundsException("Index: " + fieldIndex + ", Size: " + fieldDefinitionsMap.size());
        }
        for (FieldDefinition fieldDefinition : fieldDefinitionsMap.values()) {
            if (fieldIndex == fieldDefinition.getIndex()) {
                return fieldDefinition;
            }
        }
        throw new IndexOutOfBoundsException("Index: " + fieldIndex + ", Size: " + fieldDefinitionsMap.size());
    }

    public boolean hasField(String fieldName) {
        return fieldDefinitionsMap.containsKey(fieldName);
    }

    public Set<String> getFieldNames() {
        return new HashSet<String>(fieldDefinitionsMap.keySet());
    }

    public FieldType getFieldType(String fieldName) {
        final FieldDefinition fd = getField(fieldName);
        if (fd != null) {
            return fd.getType();
        }
        throw new IllegalArgumentException("Unknown field: " + fieldName);
    }

    public int getFieldClassId(String fieldName) {
        final FieldDefinition fd = getField(fieldName);
        if (fd != null) {
            return fd.getClassId();
        }
        throw new IllegalArgumentException("Unknown field: " + fieldName);
    }

    Collection<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitionsMap.values();
    }

    @Override
    public int getFieldCount() {
        return fieldDefinitionsMap.size();
    }

    public final int getFactoryId() {
        return factoryId;
    }

    public final int getClassId() {
        return classId;
    }

    public final int getVersion() {
        return version;
    }

    void setVersionIfNotSet(int version) {
        if (getVersion() < 0) {
            this.version = version;
        }
    }

    //CHECKSTYLE:OFF
    //Generated equals method has too high NPath Complexity
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassDefinitionImpl that = (ClassDefinitionImpl) o;

        if (classId != that.classId) {
            return false;
        }
        if (version != that.version) {
            return false;
        }
        if (getFieldCount() != that.getFieldCount()) {
            return false;
        }
        for (FieldDefinition fd : fieldDefinitionsMap.values()) {
            FieldDefinition fd2 = that.getField(fd.getName());
            if (fd2 == null) {
                return false;
            }
            if (!fd.equals(fd2)) {
                return false;
            }
        }

        return true;
    }
    //CHECKSTYLE:ON

    @Override
    public int hashCode() {
        int result = classId;
        result = 31 * result + version;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ClassDefinition");
        sb.append("{factoryId=").append(factoryId);
        sb.append(", classId=").append(classId);
        sb.append(", version=").append(version);
        sb.append(", fieldDefinitions=").append(fieldDefinitionsMap.values());
        sb.append('}');
        return sb.toString();
    }
}
