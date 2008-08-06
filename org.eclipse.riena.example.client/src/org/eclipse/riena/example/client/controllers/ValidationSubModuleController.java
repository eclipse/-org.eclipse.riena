/*******************************************************************************
 * Copyright (c) 2007, 2008 compeople AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    compeople AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.riena.example.client.controllers;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.riena.example.client.views.ValidationSubModuleView;
import org.eclipse.riena.navigation.ISubApplicationNode;
import org.eclipse.riena.navigation.ISubModuleNode;
import org.eclipse.riena.navigation.ui.controllers.SubApplicationController;
import org.eclipse.riena.navigation.ui.controllers.SubModuleController;
import org.eclipse.riena.ui.ridgets.IStatuslineRidget;
import org.eclipse.riena.ui.ridgets.ITextFieldRidget;
import org.eclipse.riena.ui.ridgets.ValidationTime;
import org.eclipse.riena.ui.ridgets.marker.StatuslineMessageMarkerViewer;
import org.eclipse.riena.ui.ridgets.marker.TooltipMessageMarkerViewer;
import org.eclipse.riena.ui.ridgets.validation.MaxLength;
import org.eclipse.riena.ui.ridgets.validation.MinLength;
import org.eclipse.riena.ui.ridgets.validation.RequiredField;
import org.eclipse.riena.ui.ridgets.validation.ValidCharacters;
import org.eclipse.riena.ui.ridgets.validation.ValidDate;
import org.eclipse.riena.ui.ridgets.validation.ValidEmailAddress;
import org.eclipse.riena.ui.ridgets.validation.ValidExpression;
import org.eclipse.riena.ui.ridgets.validation.ValidIntermediateDate;
import org.eclipse.riena.ui.ridgets.validation.ValidRange;

/**
 * Controller for the {@link ValidationSubModuleView} example.
 */
public class ValidationSubModuleController extends SubModuleController {

	private static final String DATE_PATTERN = "dd.MM.yyyy"; //$NON-NLS-1$

	private ITextFieldRidget txtNumbersOnly;
	private ITextFieldRidget txtNumbersOnlyDW;
	private ITextFieldRidget txtCharactersOnly;
	private ITextFieldRidget txtExpression;
	private ITextFieldRidget txtLengthLessThan5;
	private ITextFieldRidget txtRequiredLowercase;
	private ITextFieldRidget txtRange18to80;
	private ITextFieldRidget txtLength5to10;
	private ITextFieldRidget txtDate;
	private ITextFieldRidget txtEmail;

	private ITextFieldRidget lblNumbersOnly;
	private ITextFieldRidget lblNumbersOnlyDW;
	private ITextFieldRidget lblCharactersOnly;
	private ITextFieldRidget lblExpression;
	private ITextFieldRidget lblLengthLessThan5;
	private ITextFieldRidget lblRequiredLowercase;
	private ITextFieldRidget lblRange18to80;
	private ITextFieldRidget lblLength5to10;
	private ITextFieldRidget lblDate;
	private ITextFieldRidget lblEmail;

	public ValidationSubModuleController() {
		this(null);
	}

	public ValidationSubModuleController(ISubModuleNode navigationNode) {
		super(navigationNode);
	}

	public ITextFieldRidget getTxtNumbersOnly() {
		return txtNumbersOnly;
	}

	public void setTxtNumbersOnly(ITextFieldRidget txtNumbersOnly) {
		this.txtNumbersOnly = txtNumbersOnly;
	}

	public ITextFieldRidget getTxtNumbersOnlyDW() {
		return txtNumbersOnlyDW;
	}

	public void setTxtNumbersOnlyDW(ITextFieldRidget txtNumbersOnlyDW) {
		this.txtNumbersOnlyDW = txtNumbersOnlyDW;
	}

	public ITextFieldRidget getTxtCharactersOnly() {
		return txtCharactersOnly;
	}

	public void setTxtCharactersOnly(ITextFieldRidget txtCharactersOnly) {
		this.txtCharactersOnly = txtCharactersOnly;
	}

	public ITextFieldRidget getTxtExpression() {
		return txtExpression;
	}

	public void setTxtExpression(ITextFieldRidget txtExpression) {
		this.txtExpression = txtExpression;
	}

	public ITextFieldRidget getTxtLengthLessThan5() {
		return txtLengthLessThan5;
	}

	public void setTxtLengthLessThan5(ITextFieldRidget txtLengthLessThan5) {
		this.txtLengthLessThan5 = txtLengthLessThan5;
	}

	public ITextFieldRidget getTxtRange18to80() {
		return txtRange18to80;
	}

	public void setTxtRange18to80(ITextFieldRidget txtRange18to80) {
		this.txtRange18to80 = txtRange18to80;
	}

	public ITextFieldRidget getTxtLength5to10() {
		return txtLength5to10;
	}

	public void setTxtLength5to10(ITextFieldRidget txtLength5to10) {
		this.txtLength5to10 = txtLength5to10;
	}

	public ITextFieldRidget getTxtDate() {
		return txtDate;
	}

	public void setTxtDate(ITextFieldRidget txtDate) {
		this.txtDate = txtDate;
	}

	public ITextFieldRidget getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(ITextFieldRidget txtEmail) {
		this.txtEmail = txtEmail;
	}

	public ITextFieldRidget getLblNumbersOnly() {
		return lblNumbersOnly;
	}

	public void setLblNumbersOnly(ITextFieldRidget lblNumbersOnly) {
		this.lblNumbersOnly = lblNumbersOnly;
	}

	public ITextFieldRidget getLblNumbersOnlyDW() {
		return lblNumbersOnlyDW;
	}

	public void setLblNumbersOnlyDW(ITextFieldRidget lblNumbersOnlyDW) {
		this.lblNumbersOnlyDW = lblNumbersOnlyDW;
	}

	public ITextFieldRidget getLblCharactersOnly() {
		return lblCharactersOnly;
	}

	public void setLblCharactersOnly(ITextFieldRidget lblCharactersOnly) {
		this.lblCharactersOnly = lblCharactersOnly;
	}

	public ITextFieldRidget getLblExpression() {
		return lblExpression;
	}

	public void setLblExpression(ITextFieldRidget lblExpression) {
		this.lblExpression = lblExpression;
	}

	public ITextFieldRidget getLblLengthLessThan5() {
		return lblLengthLessThan5;
	}

	public void setLblLengthLessThan5(ITextFieldRidget lblLengthLessThan5) {
		this.lblLengthLessThan5 = lblLengthLessThan5;
	}

	public ITextFieldRidget getLblRange18to80() {
		return lblRange18to80;
	}

	public void setLblRange18to80(ITextFieldRidget lblRange18to80) {
		this.lblRange18to80 = lblRange18to80;
	}

	public ITextFieldRidget getLblLength5to10() {
		return lblLength5to10;
	}

	public void setLblLength5to10(ITextFieldRidget lblLength5to10) {
		this.lblLength5to10 = lblLength5to10;
	}

	public ITextFieldRidget getLblDate() {
		return lblDate;
	}

	public void setLblDate(ITextFieldRidget lblDate) {
		this.lblDate = lblDate;
	}

	public ITextFieldRidget getLblEmail() {
		return lblEmail;
	}

	public void setLblEmail(ITextFieldRidget lblEmail) {
		this.lblEmail = lblEmail;
	}

	public ITextFieldRidget getTxtRequiredLowercase() {
		return txtRequiredLowercase;
	}

	public void setTxtRequiredLowercase(ITextFieldRidget txtRequiredLowercase) {
		this.txtRequiredLowercase = txtRequiredLowercase;
	}

	public ITextFieldRidget getLblRequiredLowercase() {
		return lblRequiredLowercase;
	}

	public void setLblRequiredLowercase(ITextFieldRidget lblRequiredLowercase) {
		this.lblRequiredLowercase = lblRequiredLowercase;
	}

	@Override
	public void afterBind() {
		super.afterBind();
		initRidgets();
	}

	/**
	 * Binds and updates the ridgets.
	 */
	private void initRidgets() {

		// on edit validation

		txtNumbersOnly.addValidationRule(new ValidCharacters(ValidCharacters.VALID_NUMBERS),
				ValidationTime.ON_UI_CONTROL_EDIT);
		txtNumbersOnly.addValidationMessage("Only numbers are allowed!"); //$NON-NLS-1$
		txtNumbersOnly.bindToModel(getTextValue(lblNumbersOnly));

		txtNumbersOnlyDW.addValidationRule(new ValidCharacters(ValidCharacters.VALID_NUMBERS),
				ValidationTime.ON_UI_CONTROL_EDIT);
		txtNumbersOnlyDW.addValidationMessage("Only numbers are allowed!"); //$NON-NLS-1$
		txtNumbersOnlyDW.setDirectWriting(true);
		txtNumbersOnlyDW.bindToModel(getTextValue(lblNumbersOnlyDW));

		txtCharactersOnly.addValidationRule(new ValidCharacters(ValidCharacters.VALID_LETTER),
				ValidationTime.ON_UI_CONTROL_EDIT);
		txtCharactersOnly.addValidationMessage("Only characters are allowed!"); //$NON-NLS-1$
		txtCharactersOnly.bindToModel(getTextValue(lblCharactersOnly));

		txtExpression.addValidationRule(new ValidExpression("^PDX[0-9]{2}$"), ValidationTime.ON_UI_CONTROL_EDIT); //$NON-NLS-1$
		txtExpression.addValidationMessage("The text does not match with the expression (PDX##)!"); //$NON-NLS-1$
		txtExpression.bindToModel(getTextValue(lblExpression));
		txtExpression.setText("PDX97"); //$NON-NLS-1$

		txtLengthLessThan5.addValidationRule(new MaxLength(5), ValidationTime.ON_UI_CONTROL_EDIT);
		txtLengthLessThan5.addValidationMessage("The text is longer than 5 characters!"); //$NON-NLS-1$
		txtLengthLessThan5.bindToModel(getTextValue(lblLengthLessThan5));

		txtRequiredLowercase.addValidationRule(new RequiredField(), ValidationTime.ON_UI_CONTROL_EDIT);
		txtRequiredLowercase.addValidationRule(new ValidCharacters(ValidCharacters.VALID_LOWERCASE),
				ValidationTime.ON_UI_CONTROL_EDIT);
		txtRequiredLowercase.addValidationMessage("Only lowercase characters are allowed!"); //$NON-NLS-1$
		txtRequiredLowercase.bindToModel(getTextValue(lblRequiredLowercase));

		// on update validation

		txtRange18to80.addValidationRule(new RequiredField(), ValidationTime.ON_UPDATE_TO_MODEL);
		txtRange18to80.addValidationRule(new ValidRange(18, 80), ValidationTime.ON_UPDATE_TO_MODEL);
		txtRange18to80.bindToModel(getTextValue(lblRange18to80));

		txtLength5to10.addValidationRule(new MinLength(5), ValidationTime.ON_UPDATE_TO_MODEL);
		txtLength5to10.addValidationRule(new MaxLength(10), ValidationTime.ON_UPDATE_TO_MODEL);
		txtLength5to10.bindToModel(getTextValue(lblLength5to10));

		// complex validation

		txtDate.addValidationRule(new ValidIntermediateDate(DATE_PATTERN), ValidationTime.ON_UI_CONTROL_EDIT);
		txtDate.addValidationRule(new ValidDate(DATE_PATTERN), ValidationTime.ON_UPDATE_TO_MODEL);
		txtDate.setText("25.12.2008"); //$NON-NLS-1$
		txtDate.bindToModel(getTextValue(lblDate));

		txtEmail.addValidationRule(new ValidEmailAddress(), ValidationTime.ON_UI_CONTROL_EDIT);
		txtEmail.bindToModel(getTextValue(lblEmail));
		txtEmail.setText("elmer@foo.bar"); //$NON-NLS-1$

		IStatuslineRidget statuslineRidget = getSubApplicationController().getStatuslineRidget();
		StatuslineMessageMarkerViewer statuslineMessageMarkerViewer = new StatuslineMessageMarkerViewer(
				statuslineRidget);
		statuslineMessageMarkerViewer.addRidget(txtNumbersOnly);
		statuslineMessageMarkerViewer.addRidget(txtNumbersOnlyDW);
		statuslineMessageMarkerViewer.addRidget(txtCharactersOnly);
		statuslineMessageMarkerViewer.addRidget(txtExpression);
		statuslineMessageMarkerViewer.addRidget(txtLengthLessThan5);
		statuslineMessageMarkerViewer.addRidget(txtRequiredLowercase);

		TooltipMessageMarkerViewer tooltipMessageMarkerViewer = new TooltipMessageMarkerViewer();
		tooltipMessageMarkerViewer.addRidget(txtNumbersOnly);

	}

	private IObservableValue getTextValue(ITextFieldRidget bean) {
		return BeansObservables.observeValue(bean, ITextFieldRidget.PROPERTY_TEXT);
	}

	/**
	 * Returns the controller of the parent sub-application.
	 * 
	 * @return sub-application controller
	 */
	private SubApplicationController getSubApplicationController() {
		return (SubApplicationController) getNavigationNode().getParentOfType(ISubApplicationNode.class)
				.getPresentation();
	}

}
