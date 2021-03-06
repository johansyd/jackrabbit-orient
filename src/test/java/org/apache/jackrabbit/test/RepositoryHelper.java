/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.test;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to get access to {@link javax.jcr.Session} instances.
 */
public class RepositoryHelper {

    /**
     * Repository stub reference.
     */
    private RepositoryStub repoStub;

    /**
     * Overlay configuration.
     */
    private Map configuration = new HashMap();

    /**
     * Creates a repository helper with configuration from
     * <code>repositoryStubImpl.properties</code> file.
     */
    public RepositoryHelper() {
    }

    /**
     * Creates a repository helper with additional configuration parameters.
     *
     * @param config configuration which overlays the values from the property
     *   file.
     */
    public RepositoryHelper(Map config) {
        configuration.putAll(config);
    }

    /**
     * Returns the repository instance to test.
     * @return the repository instance to test.
     * @throws javax.jcr.RepositoryException if the repository could not be obtained.
     */
    public Repository getRepository() throws RepositoryException {
        try {
            if (repoStub == null) {
                repoStub = RepositoryStub.getInstance(configuration);
            }
            return repoStub.getRepository();
        } catch (RepositoryStubException e) {
            throw new RepositoryException("Failed to get Repository instance.", e);
        }
    }

    /**
     * Returns a superuser <code>Session</code> of the default workspace. The
     * returned <code>Session</code> has read and write access to the whole
     * workspace.
     * @return a superuser <code>Session</code>.
     * @throws javax.jcr.RepositoryException if login to the repository failed.
     */
    public Session getSuperuserSession() throws RepositoryException {
        return getSuperuserSession(null);
    }

    /**
     * Returns a superuser <code>Session</code> of the workspace with name
     * <code>workspaceName</code>. The returned <code>Session</code> has read
     * and write access to the whole workspace.
     * @return a superuser <code>Session</code>.
     * @throws javax.jcr.RepositoryException if login to the repository failed.
     */
    public Session getSuperuserSession(String workspaceName) throws RepositoryException {
        return getRepository().login(repoStub.getSuperuserCredentials(), workspaceName);
    }

    /**
     * Returns a <code>Session</code> of the default workspace with read and
     * write access to the workspace.
     * @return a <code>Session</code> with read and write access.
     * @throws javax.jcr.RepositoryException if login to the repository failed.
     */
    public Session getReadWriteSession() throws RepositoryException {
        return getReadWriteSession(null);
    }

    /**
     * Returns a <code>Session</code> of the workspace with name
     * <code>workspaceName</code> with read and write access to the workspace.
     * @return a <code>Session</code> with read and write access.
     * @throws javax.jcr.RepositoryException if login to the repository failed.
     */
    public Session getReadWriteSession(String workspaceName) throws RepositoryException {
        return getRepository().login(repoStub.getReadWriteCredentials(), workspaceName);
    }

    /**
     * Returns a <code>Session</code> of the default workspace with read only
     * access to the workspace.
     * @return a <code>Session</code> with read only.
     * @throws javax.jcr.RepositoryException if login to the repository failed.
     */
    public Session getReadOnlySession() throws RepositoryException {
        return getReadOnlySession(null);
    }

    /**
     * Returns a <code>Session</code> of the workspace with name
     * <code>workspaceName</code> with read only access to the workspace.
     * @return a <code>Session</code> with read only access.
     * @throws javax.jcr.RepositoryException if login to the repository failed.
     */
    public Session getReadOnlySession(String workspaceName) throws RepositoryException {
        return getRepository().login(repoStub.getReadOnlyCredentials(), workspaceName);
    }

    /**
     * Returns the value of the configuration property with specified
     * <code>name</code>. If the property does not exist <code>null</code> is
     * returned.
     * <p>
     * Configuration properties are defined in the file:
     * <code>repositoryStubImpl.properties</code>.
     *
     * @param name the name of the property to retrieve.
     * @return the value of the property or <code>null</code> if non existent.
     * @throws javax.jcr.RepositoryException if the configuration file cannot be found.
     */
    public String getProperty(String name) throws RepositoryException {
        // force assignment of repoStub
        getRepository();
        return repoStub.getProperty(name);
    }

    /**
     * Returns the read-only credentials created from the username
     * and password specified in the configuration properties.
     * Configuration properties are defined in the file:
     * <code>repositoryStubImpl.properties</code>.
     *
     * @return read-only <code>Credentials</code>
     */
    public Credentials getReadOnlyCredentials() {
        return repoStub.getReadOnlyCredentials();
    }

    /**
     * Returns the read-write credentials created from the username
     * and password specified in the configuration properties.
     * Configuration properties are defined in the file:
     * <code>repositoryStubImpl.properties</code>.
     *
     * @return read-write <code>Credentials</code>
     */
    public Credentials getReadWriteCredentials() {
        return repoStub.getReadWriteCredentials();
    }

    /**
     * Returns the superuser credentials created from the username
     * and password specified in the configuration properties.
     * Configuration properties are defined in the file:
     * <code>repositoryStubImpl.properties</code>.
     *
     * @return superuser <code>Credentials</code>
     */
    public Credentials getSuperuserCredentials() {
        return repoStub.getSuperuserCredentials();
    }
    
    /**
     * Returns a {@link java.security.Principal} identifiying a known user.
     * @param session
     */
    public Principal getKnownPrincipal(Session session) throws RepositoryException {
        return repoStub.getKnownPrincipal(session);
    }
    
    /**
     * Returns a {@link java.security.Principal} identifiying an unknown user.
     * @param session
     */
    public Principal getUnknownPrincipal(Session session) throws NotExecutableException, RepositoryException {
        return repoStub.getUnknownPrincipal(session);
    }
}
