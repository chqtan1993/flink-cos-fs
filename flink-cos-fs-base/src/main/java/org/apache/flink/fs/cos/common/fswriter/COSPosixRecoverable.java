/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink.fs.cos.common.fswriter;

import org.apache.flink.core.fs.RecoverableWriter.CommitRecoverable;
import org.apache.flink.core.fs.RecoverableWriter.ResumeRecoverable;
import static org.apache.flink.util.Preconditions.checkArgument;
import static org.apache.flink.util.Preconditions.checkNotNull;
import org.apache.hadoop.fs.Path;


/**
 * An implementation of the resume and commit descriptor objects for Hadoop's
 * file system abstraction.
 */
class COSPosixRecoverable implements CommitRecoverable, ResumeRecoverable {

    /** The file path for the final result file. */
    private final Path targetFile;

    /** The file path of the staging file. */
    private final Path tempFile;

    /** The position to resume from. */
    private final long offset;

    /**
     * Creates a resumable for the given file at the given position.
     *
     * @param targetFile The file to resume.
     * @param offset The position to resume from.
     */
    COSPosixRecoverable(Path targetFile, Path tempFile, long offset) {
        checkArgument(offset >= 0, "offset must be >= 0");
        this.targetFile = checkNotNull(targetFile, "targetFile");
        this.tempFile = checkNotNull(tempFile, "tempFile");
        this.offset = offset;
    }

    public Path targetFile() {
        return targetFile;
    }

    public Path tempFile() {
        return tempFile;
    }

    public long offset() {
        return offset;
    }

    @Override
    public String toString() {
        return "COSMergeRecoverable " + tempFile + " @ " + offset + " -> " + targetFile;
    }
}
