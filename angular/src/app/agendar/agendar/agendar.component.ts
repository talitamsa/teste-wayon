import { Component } from '@angular/core';
import { Agendar } from '../model/agendar';
import { AgendarService } from '../service/agendar.service';
import { Observable, catchError, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-agendar',
  templateUrl: './agendar.component.html',
  styleUrls: ['./agendar.component.scss']
})
export class AgendarComponent {

  transferencias$: Observable<Agendar[]>;

  displayedColumns = ['contaOrigem', 'contaDestino', 'valorTransferencia', 'taxa',
  'dataTransferencia', 'dataAgendada'];

  constructor(private agendarService: AgendarService,
    public dialog: MatDialog
    ) {
    this.transferencias$ = this.agendarService.list()
    .pipe(
      catchError(error => {
        this.onError('Erro ao carregar dados!');
        return of([])
      })
    );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

}
