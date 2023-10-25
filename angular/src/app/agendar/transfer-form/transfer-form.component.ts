import { Component } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
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

  form: UntypedFormGroup;

  constructor(private formBuilder: UntypedFormBuilder,
    private service: AgendarService,
    private snackBar: MatSnackBar,
    private location: Location,
    private datePipe: DatePipe) {
    this.form = this.formBuilder.group({
      contaOrigem: [null],
      contaDestino: [null],
      valorTransferencia: [null],
      dataTransferencia: [null]
    });
  }

  ngOnInit(): void {
    
  }

  onSubmit() {
    const dataTransferenciaControl = this.form.get('dataTransferencia');

    if (dataTransferenciaControl) {
      const formattedDataTransferencia = this.datePipe.transform(dataTransferenciaControl.value, 'yyyy-MM-dd');

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
    this.snackBar.open('Erro ao salvar transferência. Consulte o console para mais informações.', '', { duration: 2000 });
  }



  onCancel() {
    this.location.back();
  }

}
