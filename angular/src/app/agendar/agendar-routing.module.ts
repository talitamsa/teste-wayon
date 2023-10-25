import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AgendarComponent } from './agendar/agendar.component';
import { TransferFormComponent } from './transfer-form/transfer-form.component';

const routes: Routes = [
  { path: '', component: AgendarComponent },
  { path: 'new', component: TransferFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgendarRoutingModule { }
