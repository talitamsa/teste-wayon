export interface Agendar {
  _id: string;
  contaOrigem: number;
  contaDestino: number;
  valorTransferencia: number;
  taxa: number;
  dataTransferencia: string;
  dataAgendada: string;
}
