/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.is.migration.service.v580.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IDPIDColumnAdditionInH2DAO {

    private static IDPIDColumnAdditionInH2DAO instance = new IDPIDColumnAdditionInH2DAO();

    private IDPIDColumnAdditionInH2DAO() { }

    public static IDPIDColumnAdditionInH2DAO getInstance() {

        return instance;
    }

    /**
     * Method to persist IDP ID in database
     *
     * @param connection
     * @throws SQLException
     */
    public void addIdpIdColumn(Connection connection, String tableName, String columnName) throws SQLException {

        StringBuilder addColumnWithDefaultVal = new StringBuilder();
        addColumnWithDefaultVal.append("ALTER TABLE ");
        addColumnWithDefaultVal.append(tableName);
        addColumnWithDefaultVal.append(" ADD COLUMN ");
        addColumnWithDefaultVal.append(columnName);
        addColumnWithDefaultVal.append(" INTEGER NOT NULL DEFAULT -1");

        StringBuilder dropColumnDefaultVal = new StringBuilder();
        dropColumnDefaultVal.append("ALTER TABLE ");
        dropColumnDefaultVal.append(tableName);
        dropColumnDefaultVal.append(" ALTER COLUMN ");
        dropColumnDefaultVal.append(columnName);
        dropColumnDefaultVal.append(" DROP DEFAULT");

        executeUpdate(connection, addColumnWithDefaultVal);
        executeUpdate(connection, dropColumnDefaultVal);
    }

    private void executeUpdate(Connection connection, StringBuilder addColumnWithDefaultVal) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(addColumnWithDefaultVal.toString())) {

            preparedStatement.executeUpdate();
        }
    }
}
