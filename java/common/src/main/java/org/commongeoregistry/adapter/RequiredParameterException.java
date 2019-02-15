/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Common Geo Registry Adapter(tm).
 *
 * Common Geo Registry Adapter(tm) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Common Geo Registry Adapter(tm) is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Common Geo Registry Adapter(tm).  If not, see <http://www.gnu.org/licenses/>.
 */
package org.commongeoregistry.adapter;

public class RequiredParameterException extends RuntimeException
{
  /**
   * 
   */
  private static final long serialVersionUID = 4747172271720334348L;

  /**
   * 
   */

  private String            parameter;

  public RequiredParameterException(String methodName, String parameter)
  {
    super("Method [" + methodName + "] requires a parameter value for the parameter named [" + parameter + "]");

    this.parameter = parameter;
  }

  public String getParameter()
  {
    return parameter;
  }
}
