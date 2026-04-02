export interface Client {
  id: string;
  ragione_sociale: string;
  indirizzo: string;
  cap?: string;
  tel?: string;
  email?: string;
  vettore: boolean;
  fasce_orarie_consegna?: { giorno: string, dalle: string; alle: string }[];
  fasce_orarie_ritiro?: { giorno: string, dalle: string; alle: string }[];
}

export interface AdrElemento {
  id: number;
  label: string;
}

export interface MerceTipo {
  id: number;
  descrizione: string;
}

export interface AdrData {
  codice_imballaggio: string;
  qta: string;
  um: string;
  id_adr_elemento: number | string;
}

export interface MerceItem {
  segnacollo: string;
  merce_tipo: string | number;
  peso_kg?: string;
  volume_m3?: string;
  fragile?: boolean;
  epal?: boolean;
  id_epal?: string;
  _hasAdr?: boolean;
  adr?: AdrData;
}

export interface IncaricoPaylod {
  ldv: string;
  ddt?: string;
  data_consegna_prevista: string;
  valore_doganale?: number;
  ritiro: boolean;
  valore_assicurazione?: number;
  mitt: string | Partial<Client>;
  dest: string | Partial<Client>;
  vett_mitt?: string | Partial<Client>;
  vett_dest?: string | Partial<Client>;
  contrassegno?: boolean;
  contrassegno_valore?: number;
  contrassegno_tipo?: number;
  merce: Omit<MerceItem, '_hasAdr'>[];
  note?: string;
}

export interface FormValues {
  ldv: string;
  ddt: string;
  data_consegna_prevista: string;
  mitt: string | object | null;
  dest: string | object | null;
  vett_mitt: string | object | null;
  vett_dest: string | object | null;
  ritiro: boolean;
  valore_doganale: string;
  valore_assicurazione: string;
  contrassegno: boolean;
  contrassegno_valore: string;
  contrassegno_tipo: string;
  note: string;
  merce: MerceItem[];
}
