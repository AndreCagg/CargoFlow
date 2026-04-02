import type { Client, AdrElemento, MerceTipo } from './types';

export const MOCK_CLIENTS: Client[] = [
  { id: 'bd21595e-73f0-4dae-b145-f641582782d4', ragione_sociale: 'Trasporti Rossi S.r.l.', indirizzo: 'Via Roma 12, Milano', vettore: false },
  { id: 'f564e3dd-7006-4882-8fe5-db2a32d8dde2', ragione_sociale: 'Logistics Plus S.p.A.', indirizzo: 'Via Po 45, Torino', vettore: false },
  { id: '39dc1fe8-10c1-4e1d-b007-4661fc72b0ba', ragione_sociale: 'Vettore Nord S.r.l.', indirizzo: 'Via Venezia 7, Venezia', vettore: true },
  { id: '731e50fe-1cd4-4094-9a96-6e421563b09f', ragione_sociale: 'Express Sud S.p.A.', indirizzo: 'Via Napoli 33, Napoli', vettore: true },
];

export const MOCK_ADR: AdrElemento[] = [
  { id: 1234, label: 'UN1234 – Liquido infiammabile, n.a.s.' },
  { id: 2345, label: 'UN2345 – Gas compresso, n.a.s.' },
  { id: 3456, label: 'UN3456 – Solido corrosivo, n.a.s.' },
];

export const MERCE_TIPI: MerceTipo[] = [
  { id: 10, descrizione: 'Collo generico' },
  { id: 20, descrizione: 'Bancale/Pallet' },
  { id: 30, descrizione: 'Busta/Documento' },
  { id: 40, descrizione: 'Collo fragile' },
];

export const API_BASE = 'http://localhost:8090/api/v1.0';

export const NAV_MODULES = [
  { id: 'dashboard', label: 'Dashboard', group: '' },
  { id: 'incarico',  label: 'Nuovo incarico', group: 'Incarichi' },
  { id: 'bordero',   label: 'Borderò', group: 'Incarichi' },
  { id: 'clienti',   label: 'Clienti', group: 'Anagrafica' },
  { id: 'veicoli',   label: 'Veicoli', group: 'Anagrafica' },
] as const;

export type PageId = typeof NAV_MODULES[number]['id'];
