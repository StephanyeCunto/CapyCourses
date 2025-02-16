package com.view.login_cadastro.cadastro.valid;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class CadastroValid {
  private static final ValidationSupport validationSupport = new ValidationSupport();
  private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
  private static final String NAME_REGEX = "^[A-ZÀ-ÿa-zà-ÿ]+(?: [A-ZÀ-ÿa-zà-ÿ]+)+$";
  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
  private static final int MIN_PASSWORD_LENGTH = 6;

  // Constantes de mensagens
  private static final String MSG_NOME_OBRIGATORIO = "Digite seu nome completo";
  private static final String MSG_NOME_INVALIDO = "Formato inválido (ex: João Silva)";
  private static final String MSG_EMAIL_OBRIGATORIO = "Digite seu email";
  private static final String MSG_EMAIL_INVALIDO = "Formato inválido (ex: nome@provedor.com)";
  private static final String MSG_SENHA_TAMANHO = "Crie uma senha segura (mínimo 6 caracteres)";
  private static final String MSG_SENHA_OBRIGATORIA = "Digite uma senha";
  private static final String MSG_CONFIRMACAO_SENHA = "As senhas precisam ser iguais";

  @FXML private TextField textFieldName;
  @FXML private TextField textFieldEmail;
  @FXML private PasswordField passwordFieldPassword;
  @FXML private PasswordField passwordFieldPasswordConfirm;
  @FXML private Label userNameErrorLabel;
  @FXML private Label userEmailErrorLabel;
  @FXML private Label passwordErrorLabel;
  @FXML private Label passwordConfirmErrorLabel;

  @SuppressWarnings("unused")
  public void setupInitialState(
      TextField textFieldName,
      TextField textFieldEmail,
      PasswordField passwordFieldPassword,
      PasswordField passwordFieldPasswordConfirm,
      Label userNameErrorLabel,
      Label userEmailErrorLabel,
      Label passwordErrorLabel,
      Label passwordConfirmErrorLabel) {

    initComponents(
        textFieldName,
        textFieldEmail,
        passwordFieldPassword,
        passwordFieldPasswordConfirm,
        userNameErrorLabel,
        userEmailErrorLabel,
        passwordErrorLabel,
        passwordConfirmErrorLabel);

    configurarValidacoes();
  }

  private void initComponents(
      TextField textFieldName,
      TextField textFieldEmail,
      PasswordField passwordFieldPassword,
      PasswordField passwordFieldPasswordConfirm,
      Label userNameErrorLabel,
      Label userEmailErrorLabel,
      Label passwordErrorLabel,
      Label passwordConfirmErrorLabel) {

    this.textFieldName = textFieldName;
    this.textFieldEmail = textFieldEmail;
    this.passwordFieldPassword = passwordFieldPassword;
    this.passwordFieldPasswordConfirm = passwordFieldPasswordConfirm;
    this.userNameErrorLabel = userNameErrorLabel;
    this.userEmailErrorLabel = userEmailErrorLabel;
    this.passwordErrorLabel = passwordErrorLabel;
    this.passwordConfirmErrorLabel = passwordConfirmErrorLabel;

    ocultarMensagensErro();
  }

  private void ocultarMensagensErro() {
    userNameErrorLabel.setVisible(false);
    userEmailErrorLabel.setVisible(false);
    passwordErrorLabel.setVisible(false);
    passwordConfirmErrorLabel.setVisible(false);
  }

  private void configurarValidacoes() {
    configurarValidacaoNome();
    configurarValidacaoEmail();
    configurarValidacaoSenha();
    configurarConfirmacaoSenha();
  }

  private void configurarValidacaoNome() {
    textFieldName.textProperty().addListener((obs, oldVal, newVal) -> atualizarEstadoNome(newVal));

    textFieldName
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              if (!isNowFocused) validarCampoNome();
            });
  }

  private void atualizarEstadoNome(String valor) {
    if (valor.matches(NAME_REGEX)) {
      limparErro(textFieldName, userNameErrorLabel);
    }
  }

  private void validarCampoNome() {
    validationSupport.registerValidator(
        textFieldName,
        false,
        Validator.createPredicateValidator(
            value -> validarNome((String) value), MSG_NOME_INVALIDO));
  }

  private boolean validarNome(String valor) {
    return valor != null && !valor.trim().isEmpty() && valor.matches(NAME_REGEX);
  }

  private void configurarValidacaoEmail() {
    textFieldEmail
        .textProperty()
        .addListener((obs, oldVal, newVal) -> atualizarEstadoEmail(newVal));

    textFieldEmail
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              if (!isNowFocused) validarCampoEmail();
            });
  }

  private void atualizarEstadoEmail(String valor) {
    if (valor.matches(EMAIL_REGEX)) {
      limparErro(textFieldEmail, userEmailErrorLabel);
    }
  }

  private void validarCampoEmail() {
    validationSupport.registerValidator(
        textFieldEmail,
        false,
        Validator.createPredicateValidator(
            value -> validarEmail((String) value), MSG_EMAIL_INVALIDO));
  }

  private boolean validarEmail(String valor) {
    return valor != null && !valor.trim().isEmpty() && valor.matches(EMAIL_REGEX);
  }

  private void configurarValidacaoSenha() {
    passwordFieldPassword
        .textProperty()
        .addListener((obs, oldVal, newVal) -> atualizarEstadoSenha(newVal));

    passwordFieldPassword
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              if (!isNowFocused) validarCampoSenha();
            });
  }

  private void atualizarEstadoSenha(String valor) {
    if (valor.length() >= MIN_PASSWORD_LENGTH) {
      limparErro(passwordFieldPassword, passwordErrorLabel);
    }
  }

  private void validarCampoSenha() {
    validationSupport.registerValidator(
        passwordFieldPassword,
        false,
        Validator.createPredicateValidator(
            value -> validarSenha((String) value), MSG_SENHA_TAMANHO));
  }

  private boolean validarSenha(String valor) {
    return valor != null && !valor.trim().isEmpty() && valor.length() >= MIN_PASSWORD_LENGTH;
  }

  private void configurarConfirmacaoSenha() {
    passwordFieldPasswordConfirm
        .textProperty()
        .addListener((obs, oldVal, newVal) -> atualizarEstadoConfirmacaoSenha());

    passwordFieldPasswordConfirm
        .focusedProperty()
        .addListener(
            (obs, wasFocused, isNowFocused) -> {
              if (!isNowFocused) validarConfirmacaoSenha();
            });
  }

  private void atualizarEstadoConfirmacaoSenha() {
    if (passwordFieldPasswordConfirm.getText().equals(passwordFieldPassword.getText())) {
      limparErro(passwordFieldPasswordConfirm, passwordConfirmErrorLabel);
    }
  }

  private void validarConfirmacaoSenha() {
    validationSupport.registerValidator(
        passwordFieldPasswordConfirm,
        false,
        Validator.createPredicateValidator(
            value -> validarConfirmacaoSenha((String) value), MSG_CONFIRMACAO_SENHA));
  }

  private boolean validarConfirmacaoSenha(String valor) {
    return valor != null && valor.equals(passwordFieldPassword.getText());
  }

  private void limparErro(Control campo, Label labelErro) {
    updateErrorDisplay(campo, labelErro, true, null);
  }

  public void updateErrorDisplay(Control field, Label errorLabel, boolean isValid, String message) {
    field.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValid);
    errorLabel.setText(isValid ? "" : message);
    errorLabel.setVisible(!isValid);
    errorLabel.setManaged(!isValid);

    if (isValid) {
      field.getStyleClass().remove("error-field");
    } else {
      if (!field.getStyleClass().contains("error-field")) {
        field.getStyleClass().add("error-field");
      }
    }
  }

  public boolean validateFields() {
    boolean isValid = true;
    String nome = textFieldName.getText();
    String email = textFieldEmail.getText();
    String senha = passwordFieldPassword.getText();
    String confirmacao = passwordFieldPasswordConfirm.getText();

    // Validação do nome
    if (nome == null || nome.trim().isEmpty()) {
      updateErrorDisplay(textFieldName, userNameErrorLabel, false, MSG_NOME_OBRIGATORIO);
      isValid = false;
    } else if (!validarNome(nome)) {
      updateErrorDisplay(textFieldName, userNameErrorLabel, false, MSG_NOME_INVALIDO);
      isValid = false;
    }

    // Validação do email
    if (email == null || email.trim().isEmpty()) {
      updateErrorDisplay(textFieldEmail, userEmailErrorLabel, false, MSG_EMAIL_OBRIGATORIO);
      isValid = false;
    } else if (!validarEmail(email)) {
      updateErrorDisplay(textFieldEmail, userEmailErrorLabel, false, MSG_EMAIL_INVALIDO);
      isValid = false;
    }

    // Validação da senha
    if (senha == null || senha.trim().isEmpty()) {
      updateErrorDisplay(passwordFieldPassword, passwordErrorLabel, false, MSG_SENHA_OBRIGATORIA);
      isValid = false;
    } else if (!validarSenha(senha)) {
      updateErrorDisplay(passwordFieldPassword, passwordErrorLabel, false, MSG_SENHA_TAMANHO);
      isValid = false;
    }

    // Validação de confirmação
    if (!validarConfirmacaoSenha(confirmacao)) {
      updateErrorDisplay(
          passwordFieldPasswordConfirm, passwordConfirmErrorLabel, false, MSG_CONFIRMACAO_SENHA);
      isValid = false;
    }

    return isValid;
  }

  public ValidationSupport getValidationSupport() {
    return validationSupport;
  }
}
