/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.mappingoutput;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.mapping.MappingValueRename;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;




/*
 * Created on 02-jun-2003
 * 
 */

public class MappingOutputMeta extends BaseStepMeta implements StepMetaInterface
{
	private static Class<?> PKG = MappingOutputMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

	/*
    private String  fieldName[];

    private int     fieldType[];

    private int     fieldLength[];

    private int     fieldPrecision[];
    
    private boolean fieldAdded[];
    */
	
    private volatile List<MappingValueRename> inputValueRenames;
    private volatile List<MappingValueRename> outputValueRenames;
	
    public MappingOutputMeta()
    {
        super(); // allocate BaseStepMeta
        inputValueRenames = new ArrayList<MappingValueRename>();
        inputValueRenames = new ArrayList<MappingValueRename>();
    }

    /*
     * @return Returns the fieldLength.
     *
    public int[] getFieldLength()
    {
        return fieldLength;
    }
    */

    /*
     * @param fieldLength The fieldLength to set.
    public void setFieldLength(int[] fieldLength)
    {
        this.fieldLength = fieldLength;
    }
     */

    /*
     * @return Returns the fieldName.
    public String[] getFieldName()
    {
        return fieldName;
    }
     */

    /*
     * @param fieldName The fieldName to set.
    public void setFieldName(String[] fieldName)
    {
        this.fieldName = fieldName;
    }
     */

    /*
     * @return Returns the fieldPrecision.
    public int[] getFieldPrecision()
    {
        return fieldPrecision;
    }
     */

    /*
     * @param fieldPrecision The fieldPrecision to set.
    public void setFieldPrecision(int[] fieldPrecision)
    {
        this.fieldPrecision = fieldPrecision;
    }
     */

    /*
     * @return Returns the fieldType.
    public int[] getFieldType()
    {
        return fieldType;
    }
     */

    /*
     * @param fieldType The fieldType to set.
    public void setFieldType(int[] fieldType)
    {
        this.fieldType = fieldType;
    }
     */

    /*
    public boolean[] getFieldAdded()
    {
        return fieldAdded;
    }
    
    public void setFieldAdded(boolean[] fieldAdded)
    {
        this.fieldAdded = fieldAdded;
    }
    */

    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore) throws KettleXMLException
    {
        readData(stepnode);
    }

    public Object clone()
    {
        MappingOutputMeta retval = (MappingOutputMeta) super.clone();

        /*
        int nrfields = fieldName.length;

        retval.allocate(nrfields);

        for (int i = 0; i < nrfields; i++)
        {
            retval.fieldName[i] = fieldName[i];
            retval.fieldType[i] = fieldType[i];
            retval.fieldLength[i] = fieldLength[i];
            retval.fieldPrecision[i] = fieldPrecision[i];
            retval.fieldAdded[i] = fieldAdded[i];
        }
		*/
        
        return retval;
    }

    public void allocate(int nrfields)
    {
    	/*
        fieldName      = new String[nrfields];
        fieldType      = new int[nrfields];
        fieldLength    = new int[nrfields];
        fieldPrecision = new int[nrfields];
        fieldAdded     = new boolean[nrfields];
        */
    }

    private void readData(Node stepnode) throws KettleXMLException
    {
        try
        {
        	/*
            Node fields = XMLHandler.getSubNode(stepnode, "fields"); 
            int nrfields = XMLHandler.countNodes(fields, "field"); 

            allocate(nrfields);

            for (int i = 0; i < nrfields; i++)
            {
                Node fnode = XMLHandler.getSubNodeByNr(fields, "field", i); 

                fieldName[i] = XMLHandler.getTagValue(fnode, "name"); 
                fieldType[i] = ValueMeta.getType(XMLHandler.getTagValue(fnode, "type")); 
                String slength = XMLHandler.getTagValue(fnode, "length"); 
                String sprecision = XMLHandler.getTagValue(fnode, "precision"); 

                fieldLength[i] = Const.toInt(slength, -1);
                fieldPrecision[i] = Const.toInt(sprecision, -1);

                fieldAdded[i] = "Y".equalsIgnoreCase( XMLHandler.getTagValue(fnode, "added") );  
			}
		
			*/
        }
        catch (Exception e)
        {
            throw new KettleXMLException(BaseMessages.getString(PKG, "MappingOutputMeta.Exception.UnableToLoadStepInfoFromXML"), e); 
        }
    }
    
    public String getXML()
    {
        StringBuffer retval = new StringBuffer(300);

        /*
        retval.append("    <fields>").append(Const.CR); 
        for (int i = 0; i < fieldName.length; i++)
        {
            if (fieldName[i] != null && fieldName[i].length() != 0)
            {
                retval.append("      <field>").append(Const.CR); 
                retval.append("        ").append(XMLHandler.addTagValue("name", fieldName[i]));  
                retval.append("        ").append(XMLHandler.addTagValue("type", ValueMeta.getTypeDesc(fieldType[i])));  
                retval.append("        ").append(XMLHandler.addTagValue("length", fieldLength[i]));  
                retval.append("        ").append(XMLHandler.addTagValue("precision", fieldPrecision[i]));  
                retval.append("        ").append(XMLHandler.addTagValue("added", fieldAdded[i]?"Y":"N"));   //$NON-NLS-3$ //$NON-NLS-4$
                retval.append("      </field>").append(Const.CR); 
            }
        }
        retval.append("    </fields>").append(Const.CR); 
		*/
        
        return retval.toString();
    }

    public void setDefault()
    {
        int nrfields = 0;

        allocate(nrfields);

        /*
        for (int i = 0; i < nrfields; i++)
        {
            fieldName[i] = "field" + i; 
            fieldType[i] = ValueMeta.TYPE_STRING;
            fieldLength[i] = 30;
            fieldPrecision[i] = -1;
            fieldAdded[i] = true;
        }
        */
    }

    public void getFields(RowMetaInterface r, String name, RowMetaInterface info[], StepMeta nextStep, VariableSpace space, Repository repository, IMetaStore metaStore) throws KettleStepException {
    	// It's best that this method doesn't change anything by itself.
    	// Eventually it's the Mapping step that's going to tell this step how to behave meta-data wise.
    	// It is the mapping step that tells the mapping output step what fields to rename.
    	// 
    	if (inputValueRenames!=null) {
    		for (MappingValueRename valueRename : inputValueRenames) {
    			ValueMetaInterface valueMeta = r.searchValueMeta(valueRename.getTargetValueName());
    			if (valueMeta!=null) {
    				valueMeta.setName(valueRename.getSourceValueName());
    			}
    		}
    	}
    	
    	// This is the optionally entered stuff in the output tab of the mapping dialog.
    	//
    	if (outputValueRenames!=null) {
    		for (MappingValueRename valueRename : outputValueRenames) {
    			ValueMetaInterface valueMeta = r.searchValueMeta(valueRename.getSourceValueName());
    			if (valueMeta!=null) {
    				valueMeta.setName(valueRename.getTargetValueName());
    			}
    		}
    	}
    }

    public void readRep(Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases) throws KettleException {
        try
        {
        	/*
            int nrfields = rep.countNrStepAttributes(id_step, "field_name"); 

            allocate(nrfields);

            for (int i = 0; i < nrfields; i++)
            {
                fieldName[i] = rep.getStepAttributeString(id_step, i, "field_name"); 
                fieldType[i] = ValueMeta.getType( rep.getStepAttributeString(id_step, i, "field_type") ); 
                fieldLength[i] = (int) rep.getStepAttributeInteger(id_step, i, "field_length"); 
                fieldPrecision[i] = (int) rep.getStepAttributeInteger(id_step, i, "field_precision"); 
                fieldAdded[i] = rep.getStepAttributeBoolean(id_step, i, "field_added"); 
            }
            */
        }
        catch (Exception e)
        {
            throw new KettleException(BaseMessages.getString(PKG, "MappingOutputMeta.Exception.UnexpectedErrorReadingStepInfo"), e); 
        }
    }

    public void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step) throws KettleException
    {
        try
        {
        	/*
            for (int i = 0; i < fieldName.length; i++)
            {
                if (fieldName[i] != null && fieldName[i].length() != 0)
                {
                    rep.saveStepAttribute(id_transformation, id_step, i, "field_name", fieldName[i]); 
                    rep.saveStepAttribute(id_transformation, id_step, i, "field_type", ValueMeta.getTypeDesc(fieldType[i])); 
                    rep.saveStepAttribute(id_transformation, id_step, i, "field_length", fieldLength[i]); 
                    rep.saveStepAttribute(id_transformation, id_step, i, "field_precision", fieldPrecision[i]); 
                    rep.saveStepAttribute(id_transformation, id_step, i, "field_added", fieldAdded[i]); 
                }
            }
            */
        }
        catch (Exception e)
        {
            throw new KettleException(BaseMessages.getString(PKG, "MappingOutputMeta.Exception.UnableToSaveStepInfo") + id_step, e); 
        }
    }

    public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info, VariableSpace space, Repository repository, IMetaStore metaStore)
    {
        CheckResult cr;
        if (prev == null || prev.size() == 0)
        {
            cr = new CheckResult(CheckResultInterface.TYPE_RESULT_WARNING, BaseMessages.getString(PKG, "MappingOutputMeta.CheckResult.NotReceivingFields"), stepMeta); 
            remarks.add(cr);
        }
        else
        {
            cr = new CheckResult(CheckResultInterface.TYPE_RESULT_OK, BaseMessages.getString(PKG, "MappingOutputMeta.CheckResult.StepReceivingDatasOK",prev.size() + ""), stepMeta);  
            remarks.add(cr);
        }

        // See if we have input streams leading to this step!
        if (input.length > 0)
        {
            cr = new CheckResult(CheckResultInterface.TYPE_RESULT_OK, BaseMessages.getString(PKG, "MappingOutputMeta.CheckResult.StepReceivingInfoFromOtherSteps"), stepMeta); 
            remarks.add(cr);
        }
        else
        {
            cr = new CheckResult(CheckResultInterface.TYPE_RESULT_ERROR, BaseMessages.getString(PKG, "MappingOutputMeta.CheckResult.NoInputReceived"), stepMeta); 
            remarks.add(cr);
        }
    }

    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta tr, Trans trans)
    {
        return new MappingOutput(stepMeta, stepDataInterface, cnr, tr, trans);
    }

    public StepDataInterface getStepData()
    {
        return new MappingOutputData();
    }
    
	/**
	 * @return the inputValueRenames
	 */
	public List<MappingValueRename> getInputValueRenames() {
		return inputValueRenames;
	}

	/**
	 * @param inputValueRenames the inputValueRenames to set
	 */
	public void setInputValueRenames(List<MappingValueRename> inputValueRenames) {
		this.inputValueRenames = inputValueRenames;
	}

	/**
	 * @return the outputValueRenames
	 */
	public List<MappingValueRename> getOutputValueRenames() {
		return outputValueRenames;
	}

	/**
	 * @param outputValueRenames the outputValueRenames to set
	 */
	public void setOutputValueRenames(List<MappingValueRename> outputValueRenames) {
		this.outputValueRenames = outputValueRenames;
	}
}
