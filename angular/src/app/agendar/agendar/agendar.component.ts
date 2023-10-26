import { Component, OnInit } from '@angular/core';
import { Agendar } from '../model/agendar';
import { AgendarService } from '../service/agendar.service';
import { Observable, catchError, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-agendar',
  templateUrl: './agendar.component.html',
  styleUrls: ['./agendar.component.scss']
})
export class AgendarComponent implements OnInit {

  transferencias$: Observable<Agendar[]>;

  transfers: Agendar[] = [];

  displayedColumns = ['contaOrigem', 'contaDestino', 'valorTransferencia', 'taxa',
  'dataTransferencia', 'dataAgendada', 'actions'];

  ngOnInit(): void { }

  constructor(private agendarService: AgendarService,
    public dialog: MatDialog,
    public router: Router,
    public route: ActivatedRoute,
    private snackBar: MatSnackBar
    ) {
    this.transferencias$ = this.agendarService.list()
    .pipe(
      catchError(error => {
        this.onError('Erro ao carregar dados!');
        return of([])
      })
    );
    console.log(this.transferencias$);
  }

  refresh() {
    this.transferencias$ = this.agendarService.list()
      .pipe(
        catchError(error => {
          this.onError('Erro ao carregar transferências.');
          return of([])
        })
      );
  }

  onError(errorMsg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

  onAdd() {
    this.router.navigate(['new'], {relativeTo: this.route})
  }

  onDelete(transferencia: Agendar) {
    this.agendarService.remove(transferencia._id).subscribe({
      next: (result) => {this.snackBar.open('Transferência excluída com sucesso!', '', { duration: 3000 });
      this.refresh();
    },
    error: (error) => {
      this.snackBar.open('Erro ao excluir transferência. Consulte o console para mais informações.', '', { duration: 2000 });
      console.error(error);
    }
    });
  }

}
