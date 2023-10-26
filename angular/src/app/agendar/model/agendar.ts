export interface Agendar {
  _id: number;
  contaOrigem: number;
  contaDestino: number;
  valorTransferencia: number;
  taxa: number;
  dataTransferencia: string;
  dataAgendada: string;
}
