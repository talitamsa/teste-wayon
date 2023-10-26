import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AgendarService } from '../service/agendar.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-transfer-form',
  templateUrl: './transfer-form.component.html',
  styleUrls: ['./transfer-form.component.scss']
})
export class TransferFormComponent {
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: AgendarService,
    private snackBar: MatSnackBar,
    private location: Location,
    private datePipe: DatePipe
  ) {
    this.form = this.formBuilder.group({
      contaOrigem: ['', Validators.required],
      contaDestino: ['', Validators.required],
      valorTransferencia: [null, [Validators.required, Validators.min(0)]],
      dataTransferencia: [null]
    });
  }

  onSubmit() {
    const dataTransferenciaControl = this.form.get('dataTransferencia');

    if (dataTransferenciaControl) {
      const selectedDate = dataTransferenciaControl.value;

      if (!this.myFilter(selectedDate)) {
        // Data não está dentro do intervalo permitido
        // Exiba uma mensagem de erro ou impeça o envio da transferência
        // Aqui você pode exibir uma mensagem de erro para o usuário ou tomar a ação necessária.
        this.snackBar.open('Data inválida. Selecione uma data futura até 50 dias a partir de hoje.', '', { duration: 3000 });
        return;
      }

      const formattedDataTransferencia = this.datePipe.transform(selectedDate, 'yyyy-MM-dd');

      if (formattedDataTransferencia) {
        dataTransferenciaControl.setValue(formattedDataTransferencia);

        this.service.save(this.form.value).subscribe({
          next: (result) => this.onSuccess(),
          error: (error) => this.onError()
        });
      }
    }
  }


  onSuccess() {
    this.snackBar.open('Transferência salva com sucesso!', '', { duration: 3000 });
    this.onCancel();
  }

  onError() {
    this.snackBar.open('Erro ao salvar a transferência. Consulte o console para mais informações.', '', { duration: 2000 });
  }

  onCancel() {
    this.location.back();
  }

  myFilter = (date: Date | null): boolean => {
  if (!date) {
    return false;
  }

  // Data atual
  const hoje = new Date();

  // Limpar a hora, minuto, segundo e milissegundo
  hoje.setHours(0, 0, 0, 0);

  // Data mínima permitida (hoje)
  const dataMinima = new Date(hoje);

  // Data máxima permitida (hoje + 50 dias)
  const dataMaxima = new Date(hoje);
  dataMaxima.setDate(hoje.getDate() + 50);

  return date >= dataMinima && date <= dataMaxima;
};


}
